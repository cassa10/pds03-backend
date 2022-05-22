package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.subject.SubjectRequestDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectWithCoursesResponseDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.mapper.SubjectMapper
import ar.edu.unq.pds03backend.model.Degree
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.model.Student
import ar.edu.unq.pds03backend.model.Subject
import ar.edu.unq.pds03backend.repository.*
import ar.edu.unq.pds03backend.service.ISubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SubjectService(
    @Autowired private val subjectRepository: ISubjectRepository,
    @Autowired private val degreeRepository: IDegreeRepository,
    @Autowired private val courseRepository: ICourseRepository,
    @Autowired private val userRepository: IUserRepository,
    @Autowired private val quoteRequestRepository: IQuoteRequestRepository,
) : ISubjectService {

    override fun getById(id: Long): SubjectResponseDTO {
        val subject = findSubjectByIdAndValidate(id)
        return SubjectMapper.toDTO(subject)
    }

    override fun getAll(): List<SubjectResponseDTO> = subjectRepository.findAll().map { SubjectMapper.toDTO(it) }

    @Transactional
    override fun create(subjectRequestDTO: SubjectRequestDTO) {
        //Adds all found degrees. If some degree doesn't exist, it doesn't add in subject
        val degreesFounded = findDegreesAndValidateIfAnyFound(subjectRequestDTO.degreeIds)
        validateSubjectNameAlreadyExist(subjectRequestDTO.name)
        val addedSubject = subjectRepository.save(Subject.Builder().withName(subjectRequestDTO.name).build())
        degreesFounded.forEach { it.addSubject(addedSubject) }
        degreeRepository.saveAll(degreesFounded)
    }

    @Transactional
    override fun update(id: Long, subjectRequestDTO: SubjectRequestDTO) {
        val subjectToUpdate = findSubjectByIdAndValidate(id)
        val previousDegrees = subjectToUpdate.degrees
        val degreesFounded = findDegreesAndValidateIfAnyFound(subjectRequestDTO.degreeIds)
        validateSubjectNameAlreadyExist(subjectRequestDTO.name)


        subjectToUpdate.name = subjectRequestDTO.name
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

    override fun getAllCurrent(): List<SubjectWithCoursesResponseDTO> {
        val currentCourses = courseRepository.findAll().filter { it.isCurrent() }
        val currentCoursesGroupedBySubject = currentCourses.groupBy { it.subject }
        return currentCoursesGroupedBySubject.map { SubjectMapper.toSubjectWithCoursesDTO(it.key, it.value) }
    }

    override fun getAllCurrentByDegree(idDegree: Long): List<SubjectWithCoursesResponseDTO> {
        val degree = degreeRepository.findById(idDegree)
        if (!degree.isPresent) throw DegreeNotFoundException()

        val currentFilteredCourses = courseRepository.findAll()
            .filter { course -> course.isCurrent() && course.belongsToDegree(degree.get()) }

        val currentFilteredCoursesGroupedBySubject = currentFilteredCourses.groupBy { it.subject }
        return currentFilteredCoursesGroupedBySubject.map { SubjectMapper.toSubjectWithCoursesDTO(it.key, it.value) }
    }

    override fun getAllCurrentByStudent(idStudent: Long): List<SubjectWithCoursesResponseDTO> {
        val user = userRepository.findById(idStudent)
        if (!user.isPresent || (user.isPresent && !user.get().isStudent())) throw StudentNotFoundException()
        val student = user.get() as Student

        var currentCourses = courseRepository.findAll()
            .filter { course -> course.isCurrent() && !student.passed(course.subject) && !student.studiedOrEnrolled(course.subject) }

        val currentCoursesRequested = quoteRequestRepository.findAllByStudentId(idStudent)
            .filter { it.state == QuoteState.PENDING && it.course.isCurrent() }.map { it.course }
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

    private fun validateSubjectNameAlreadyExist(name: String) {
        if (subjectRepository.existsByName(name)) throw SubjectNameAlreadyExistsException()
    }
}