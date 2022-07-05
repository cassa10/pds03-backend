package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.csv.CsvStudentCourseRegistrationRequestDTO
import ar.edu.unq.pds03backend.dto.csv.CsvStudentWithDegreeDTO
import ar.edu.unq.pds03backend.dto.degree.EnrolledDegreeResponseDTO
import ar.edu.unq.pds03backend.dto.user.RequestedSubjectsDTO
import ar.edu.unq.pds03backend.dto.user.SimpleEnrolledSubjectsDataDTO
import ar.edu.unq.pds03backend.dto.user.StudentRegisterRequestDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.model.*
import ar.edu.unq.pds03backend.repository.*
import ar.edu.unq.pds03backend.service.IPasswordService
import ar.edu.unq.pds03backend.service.IUserService
import ar.edu.unq.pds03backend.service.email.IEmailSender
import ar.edu.unq.pds03backend.service.logger.LogExecution
import ar.edu.unq.pds03backend.utils.QuoteStateHelper
import ar.edu.unq.pds03backend.utils.SemesterHelper
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class UserService(
    @Autowired private val userRepository: IUserRepository,
    @Autowired private val passwordService: IPasswordService,
    @Autowired private val quoteRequestRepository: IQuoteRequestRepository,
    @Autowired private val semesterRepository: ISemesterRepository,
    @Autowired private val emailSender: IEmailSender,
    @Autowired private val studiedDegreeRepository: IStudiedDegreeRepository,
    @Autowired private val courseRepository: ICourseRepository,
) : IUserService {
    companion object {
        private val logger = KotlinLogging.logger { }
    }
    @Transactional
    override fun createUser(user: User): User {
        val maybeUser = userRepository.findByDniOrEmail(user.dni, user.email)
        if(maybeUser.isPresent) throw UserAlreadyExistException()
        val newPassword = passwordService.generatePassword()
        user.password = passwordService.encryptPassword(newPassword)
        val savedUser = userRepository.save(user)
        emailSender.sendNewPasswordMailToUser(user, newPassword)
        return savedUser
    }

    @Transactional
    override fun update(id: Long, studentUpdateReq: StudentRegisterRequestDTO) {
        val user = getUser(id)
        if(!user.isStudent()) throw UserIsNotStudentException()
        user.dni = studentUpdateReq.getDni()
        user.email = studentUpdateReq.email
        user.firstName = studentUpdateReq.firstName
        user.lastName = studentUpdateReq.lastName
        val userWithAlreadyUsedData = userRepository.findByDniOrEmail(user.dni, user.email)
        if(userWithAlreadyUsedData.isPresent) throw UserAlreadyExistException()
        userRepository.save(user)
    }

    @Transactional
    override fun updatePassword(dni: String, password: String): User {
        val user = getUserByDni(dni)
        if (!user.isStudent()) throw UserIsNotStudentException()
        user.password = password
        return userRepository.save(user)
    }

    override fun findByEmailAndDni(email: String, dni: String): Optional<User> =
        userRepository.findByEmailAndDni(email, dni)

    override fun getById(id: Long): UserResponseDTO {
        val user = getUser(id)
        return getUserInfo(user)
    }

    override fun findByDni(dni: String): Optional<User> =
        userRepository.findByDni(dni)

    private fun getUserInfo(user: User): UserResponseDTO {
        val currentSemester = getCurrentSemester()
        var enrolledSubjects = listOf<SimpleEnrolledSubjectsDataDTO>()
        var requestedSubjects = listOf<RequestedSubjectsDTO>()
        var enrolledDegrees = listOf<EnrolledDegreeResponseDTO>()
        var legajo = ""
        var maxCoefficient = 0f
        if (user.isStudent()) {
            val student = user as Student
            legajo = student.legajo
            enrolledDegrees = student.enrolledDegrees.map {
                EnrolledDegreeResponseDTO.Mapper(it, student.getStudiedDegreeCoefficient(it), student.getStudiedDegreeByDegree(it)).map()
            }
            maxCoefficient = student.maxCoefficient()
            enrolledSubjects = student.enrolledCourses.map {
                SimpleEnrolledSubjectsDataDTO.Mapper(it).map()
            }
            requestedSubjects =
                quoteRequestRepository.findAllByStudentIdAndCourseSemesterIdAndInStates(student.id!!, currentSemester.id!!, QuoteStateHelper.getAllStates(), getSortByCreatedOnAsc()).map {
                    RequestedSubjectsDTO.Mapper(it).map()
                }
        }
        return UserResponseDTO(
            id = user.id!!,
            isStudent = user.isStudent(),
            firstName = user.firstName,
            lastName = user.lastName,
            dni = user.dni,
            email = user.email,
            legajo = legajo,
            maxCoefficient = maxCoefficient,
            enrolledDegrees = enrolledDegrees,
            enrolledSubjects = enrolledSubjects,
            requestedSubjects = requestedSubjects,
        )
    }

    override fun createOrUpdateStudents(studentsDTO: List<CsvStudentWithDegreeDTO>): Int {
        var successfulEntries = 0
        studentsDTO.forEach {
            var errorFound = false
            val maybeUser = userRepository.findByDni(it.student.dni)
            val degree = it.degree
            val studiedDegree = StudiedDegree.Builder().withDegree(degree)
                .withPlan(it.plan).withQuality(it.quality).withIsRegular(it.isRegular)
                .withRegistryState(it.registryState).withLocation(it.location)
                .build()
            if(maybeUser.isPresent.not()){
                createDegreeHistoryAndStudent(it.student, studiedDegree)
            }else{
                errorFound = addStudentEnrolledAndStudiedDegreeAndUpdate(maybeUser.get(), degree, studiedDegree)
            }
            if (errorFound.not()) {
                successfulEntries++
            }
        }
        return successfulEntries
    }

    override fun importMassiveCourseRegistration(courseRegistration: List<CsvStudentCourseRegistrationRequestDTO>): Int {
        var student: Student? = null
        var successfulEntries = 0
        courseRegistration.sortedBy { it.getStudentDni() }.forEach {
            try {
                if(student == null || student!!.dni != it.getStudentDni()){
                    student = getStudentByDni(it.getStudentDni())
                }
                val course = getCurrentCourseByExternalId(it.getCourseExternalId())
                addEnrolledCourseAndUpdateStudent(student!!, course)
                successfulEntries++
            }catch (e:Exception){
                logger.error("cannot import $it with student dni: ${it.getStudentDni()} and course externalId: ${it.getCourseExternalId()}. Exception message: ${e.message}")
            }
        }
        return successfulEntries
    }

    @Transactional
    fun addEnrolledCourseAndUpdateStudent(student: Student, course: Course) {
        if (!student.isStudyingAnyDegree(course.subject.degrees)) throw StudentNotEnrolledInSomeDegree()
        if (student.isStudyingOrEnrolled(course.subject)) throw StudentHasAlreadyEnrolledSubject()
        student.addEnrolledCourse(course)
        userRepository.save(student)
    }

    @Transactional
    fun createDegreeHistoryAndStudent(student:Student, studiedDegree: StudiedDegree) {
        studiedDegree.student = createUser(student) as Student
        studiedDegreeRepository.save(studiedDegree)
    }

    @Transactional
    fun addStudentEnrolledAndStudiedDegreeAndUpdate(user: User, degree: Degree, studiedDegree: StudiedDegree): Boolean {
        var errorFound = false
        try {
            if(user.isStudent().not()) throw UserIsNotStudentException()
            val studentToUpdate = user as Student
            studentToUpdate.addEnrolledDegree(degree)
            userRepository.save(studentToUpdate)
            studentToUpdate.addOrUpdateStudiedDegree(studiedDegree)
            studentToUpdate.degreeHistories.forEach {
                studiedDegreeRepository.save(it)
            }
        }catch (e: Exception){
            errorFound = true
            logger.error("cannot update student with dni: ${user.dni}. Exception message: ${e.message}")
        }
        return errorFound
    }

    private fun getUser(id: Long): User {
        val maybeUser = userRepository.findById(id)
        if (!maybeUser.isPresent) throw UserNotFoundException()
        return maybeUser.get()
    }

    private fun getUserByDni(dni: String): User {
        return userRepository.findByDni(dni).orElseThrow { throw UserNotFoundException() }
    }

    private fun getCurrentSemester(): Semester {
        return getSemester(SemesterHelper.currentYear, SemesterHelper.currentIsSecondSemester)
    }

    private fun getSemester(year: Int, isSndSemester: Boolean): Semester {
        val maybeSemester = semesterRepository.findByYearAndIsSndSemester(year, isSndSemester)
        if (!maybeSemester.isPresent) throw SemesterNotFoundException()
        return maybeSemester.get()
    }

    private fun getSortByCreatedOnAsc(): Sort = Sort.by(Sort.Direction.ASC, QuoteRequest.createdOnFieldName)

    private fun getStudentByDni(dni: String): Student {
        val user = getUserByDni(dni)
        if (!user.isStudent()) throw UserIsNotStudentException()
        return user as Student
    }

    private fun getCurrentCourseByExternalId(externalId: String): Course =
        courseRepository.findByExternalIdAndSemesterId(externalId, getCurrentSemester().id!!).orElseThrow { throw CourseNotFoundException() }

}