package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.csv.CsvSubjectWithPrerequisite
import ar.edu.unq.pds03backend.dto.subject.SubjectRequestDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectWithCoursesResponseDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.mapper.SubjectMapper
import ar.edu.unq.pds03backend.model.*
import ar.edu.unq.pds03backend.repository.*
import ar.edu.unq.pds03backend.service.ISubjectService
import ar.edu.unq.pds03backend.utils.QuoteStateHelper
import ar.edu.unq.pds03backend.utils.SemesterHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SubjectService(
    @Autowired private val subjectRepository: ISubjectRepository,
    @Autowired private val degreeRepository: IDegreeRepository,
    @Autowired private val courseRepository: ICourseRepository,
    @Autowired private val userRepository: IUserRepository,
    @Autowired private val quoteRequestRepository: IQuoteRequestRepository,
    @Autowired private val semesterRepository: ISemesterRepository,
    @Autowired private val configurableValidationRepository: IConfigurableValidationRepository,
    @Autowired private val moduleRepository: IModuleRepository,
    @Autowired private val externalSubjectRepository: IExternalSubjectRepository,
) : ISubjectService {

    override fun getById(id: Long): SubjectResponseDTO {
        val subject = findSubjectByIdAndValidate(id)
        return SubjectMapper.toDTO(subject)
    }

    override fun getAll(): List<SubjectResponseDTO> = subjectRepository.findAll().map { SubjectMapper.toDTO(it) }

    override fun getAll(pageable: Pageable): Page<SubjectResponseDTO> {
        return subjectRepository.findAll(pageable).map(SubjectMapper::toDTO)
    }

    @Transactional
    override fun create(subjectRequestDTO: SubjectRequestDTO) {
        //Adds all found degrees. If some degree doesn't exist, it doesn't add in subject
        val degreesFounded = findDegreesAndValidateIfAnyFound(subjectRequestDTO.degreeIds)
        val subjectsFounded = subjectRepository.findAllById(subjectRequestDTO.prerequisiteSubjects)
        val module = getModule(subjectRequestDTO.moduleId)

        val addedSubject = subjectRepository.save(
            Subject.Builder().withName(subjectRequestDTO.name).withPrerequisiteSubjects(subjectsFounded)
                .withModule(module).build()
        )

        degreesFounded.forEach { it.addSubject(addedSubject) }
        degreeRepository.saveAll(degreesFounded)
    }

    @Transactional
    override fun update(id: Long, subjectRequestDTO: SubjectRequestDTO) {
        val subjectToUpdate = findSubjectByIdAndValidate(id)
        val previousDegrees = subjectToUpdate.degrees
        val degreesFounded = findDegreesAndValidateIfAnyFound(subjectRequestDTO.degreeIds)
        val module = getModule(subjectRequestDTO.moduleId)
        subjectToUpdate.module = module
        subjectToUpdate.name = subjectRequestDTO.name
        subjectToUpdate.prerequisiteSubjects = subjectRepository.findAllById(subjectRequestDTO.prerequisiteSubjects)
        subjectRepository.save(subjectToUpdate)
        degreesFounded.forEach {
            it.addSubject(subjectToUpdate)
        }
        degreeRepository.saveAll(degreesFounded)
        val eliminatedDegrees = previousDegrees.filter { degreesFounded.none { founded -> it.id == founded.id } }
        eliminatedDegrees.forEach {
            it.deleteSubject(subjectToUpdate)
        }

        degreeRepository.saveAll(eliminatedDegrees)
    }

    @Transactional
    override fun delete(id: Long) {
        val subject = findSubjectByIdAndValidate(id)
        val degrees = subject.degrees
        val courses = courseRepository.findBySubjectId(subject.id!!)
        if (courses.isNotEmpty()) throw CannotDeleteSubjectWithCoursesException()
        degrees.forEach { it.deleteSubject(subject) }
        degreeRepository.saveAll(degrees)
        subjectRepository.delete(subject)
    }

    //TODO Refactor: Improve forEach's
    override fun createSubjects(subjectsCsv: List<CsvSubjectWithPrerequisite>) {
        val defaultModule = getDefaultModule()
        subjectsCsv.forEach {
            handleCreateOrUpdateSubject(
                Subject(id = null, name = it.subjectName, degrees = mutableListOf(it.degree), module = defaultModule, prerequisiteSubjects = mutableListOf()),
                it.degree,
                it.externalSubjectId
            )
        }
        subjectsCsv.forEach {
            if (it.prerequisiteSubjectsExternalIds.isNotEmpty()){
                updatePrerequisiteSubjects(it.externalSubjectId, it.prerequisiteSubjectsExternalIds)
            }
        }
    }

    @Transactional
    fun handleCreateOrUpdateSubject(subject: Subject, degree: Degree, externalSubjectId: String) {
        val maybeSubject = subjectRepository.findByGuaraniCode(externalSubjectId)
        val subjectSaved: Subject
        if(maybeSubject.isPresent.not()){
            subjectSaved = subjectRepository.save(subject)
            externalSubjectRepository.save(
                ExternalSubject(
                    id = null,
                    subject = subjectSaved,
                    guarani_code = externalSubjectId,
                )
            )
        }else {
            subjectSaved = maybeSubject.get()
        }
        degree.addSubject(subjectSaved)
        degreeRepository.save(degree)
    }

    @Transactional
    fun updatePrerequisiteSubjects(externalSubjectId: String, prerequisiteSubjects: List<String>) {
        val maybeSubject = subjectRepository.findByGuaraniCode(externalSubjectId)
        if(maybeSubject.isPresent){
            val subject = maybeSubject.get()
            prerequisiteSubjects.forEach {
                val maybePrerequisiteSubject = subjectRepository.findByGuaraniCode(it)
                if (maybePrerequisiteSubject.isPresent){
                    subject.addPrerequisiteSubject(maybePrerequisiteSubject.get())
                }
            }
            subjectRepository.save(subject)
        }
    }

    override fun getAllCurrent(): List<SubjectWithCoursesResponseDTO> {
        val currentSemester = getCurrentSemester()
        val currentCourses = courseRepository.findAllBySemesterId(currentSemester.id!!)
        val currentCoursesGroupedBySubject = currentCourses.groupBy { it.subject }
        return currentCoursesGroupedBySubject.map { SubjectMapper.toSubjectWithCoursesDTO(it.key, it.value) }
    }

    override fun getAllCurrentByDegree(idDegree: Long): List<SubjectWithCoursesResponseDTO> {
        val degree = getDegree(idDegree)
        val currentSemester = getCurrentSemester()
        val currentFilteredCourses = courseRepository.findAllBySemesterId(currentSemester.id!!)
            .filter { course -> course.belongsToDegree(degree) }

        val currentFilteredCoursesGroupedBySubject = currentFilteredCourses.groupBy { it.subject }
        return currentFilteredCoursesGroupedBySubject.map { SubjectMapper.toSubjectWithCoursesDTO(it.key, it.value) }
    }

    override fun getAllCurrentByStudent(idStudent: Long): List<SubjectWithCoursesResponseDTO> {
        val user = userRepository.findById(idStudent)
        if (!user.isPresent || (user.isPresent && !user.get().isStudent())) throw StudentNotFoundException()
        val student = user.get() as Student

        val currentSemester = getCurrentSemester()
        var currentCourses = courseRepository.findAllBySemesterId(currentSemester.id!!)
            .filter(handleGetCurrentCoursesFilter(student))

        //TODO: Refactor to jpql
        val currentCoursesRequested =
            quoteRequestRepository.findAllCoursesWithQuoteRequestInStatesAndStudentIdAndCourseSemesterId(
                QuoteStateHelper.getPendingStates(), idStudent, currentSemester.id!!
            )
        if (currentCoursesRequested.isNotEmpty())
            currentCourses = currentCourses.minus(currentCoursesRequested.toSet())

        val currentCoursesGroupedBySubject = currentCourses.groupBy { it.subject }
        return currentCoursesGroupedBySubject.map { SubjectMapper.toSubjectWithCoursesDTO(it.key, it.value) }
    }

    private fun findSubjectByIdAndValidate(id: Long): Subject {
        val maybeSubject = subjectRepository.findById(id)
        if (!maybeSubject.isPresent) throw SubjectNotFoundException()
        return maybeSubject.get()
    }

    private fun findDegreesAndValidateIfAnyFound(degreeIds: Collection<Long>): Collection<Degree> {
        //Adds all found degrees. If some degree doesn't exist, it doesn't add in subject
        val degrees = degreeRepository.findAllById(degreeIds).toList()
        if (degrees.isEmpty()) throw NotFoundAnyDegreeException()
        return degrees
    }

    private fun getCurrentSemester(): Semester {
        return getSemester(SemesterHelper.currentYear, SemesterHelper.currentIsSecondSemester)
    }

    private fun getSemester(year: Int, isSndSemester: Boolean): Semester {
        val maybeSemester = semesterRepository.findByYearAndIsSndSemester(year, isSndSemester)
        if (!maybeSemester.isPresent) throw SemesterNotFoundException()
        return maybeSemester.get()
    }

    private fun getPrerequisiteSubjectsValidation(): ConfigurableValidation {
        val maybeConfigValidation = configurableValidationRepository.findByValidation(Validation.PREREQUISITE_SUBJECTS)
        if (maybeConfigValidation.isPresent.not()) throw PrerequisiteSubjectsValidationNotFoundException()
        return maybeConfigValidation.get()
    }

    private fun handleGetCurrentCoursesFilter(student: Student): (Course) -> Boolean {
        if (getPrerequisiteSubjectsValidation().active) {
            return { course -> student.canCourseSubjectWithPrerequisiteSubjects(course.subject) }
        }
        return { course -> student.canCourseSubject(course.subject) }
    }

    private fun getDefaultModule(): Module =
        moduleRepository.findByName("Núcleo de orientación").orElseThrow {throw ModuleNotFoundException()}

    private fun getModule(moduleId: Long): Module =
        moduleRepository.findById(moduleId).orElseThrow { throw ModuleNotFoundException() }

    private fun getDegree(idDegree: Long): Degree =
        degreeRepository.findById(idDegree).orElseThrow { throw DegreeNotFoundException() }
}