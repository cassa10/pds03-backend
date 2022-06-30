package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.degree.EnrolledDegreeResponseDTO
import ar.edu.unq.pds03backend.dto.user.RequestedSubjectsDTO
import ar.edu.unq.pds03backend.dto.user.SimpleEnrolledSubjectsDataDTO
import ar.edu.unq.pds03backend.dto.user.StudentRegisterRequestDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.exception.SemesterNotFoundException
import ar.edu.unq.pds03backend.exception.UserAlreadyExistException
import ar.edu.unq.pds03backend.exception.UserIsNotStudentException
import ar.edu.unq.pds03backend.exception.UserNotFoundException
import ar.edu.unq.pds03backend.model.*
import ar.edu.unq.pds03backend.repository.IUserRepository
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.service.IPasswordService
import ar.edu.unq.pds03backend.service.IUserService
import ar.edu.unq.pds03backend.service.email.IEmailSender
import ar.edu.unq.pds03backend.utils.QuoteStateHelper
import ar.edu.unq.pds03backend.utils.SemesterHelper
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
) : IUserService {
    @Transactional
    override fun createStudent(user: User): User {
        if(!user.isStudent()) throw UserIsNotStudentException()
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
                EnrolledDegreeResponseDTO.Mapper(it, student.getStudiedDegreeCoefficient(it)).map()
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

    override fun createOrUpdateStudents(students: List<Student>) {
        students.forEach {
            val maybeUser = userRepository.findByDni(it.dni)
            if(maybeUser.isPresent.not()){
                createStudent(it)
            }else{
                addStudentEnrolledDegreesAndUpdate(maybeUser.get(), it.enrolledDegrees)
            }
        }
    }

    @Transactional
    fun addStudentEnrolledDegreesAndUpdate(user: User, degrees: Collection<Degree>) {
        if(user.isStudent().not()) throw UserIsNotStudentException()
        val studentToUpdate = user as Student
        degrees.forEach { studentToUpdate.addEnrolledDegree(it) }
        userRepository.save(studentToUpdate)
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
}