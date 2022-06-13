package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.degree.EnrolledDegreeResponseDTO
import ar.edu.unq.pds03backend.dto.user.RequestedSubjectsDTO
import ar.edu.unq.pds03backend.dto.user.SimpleEnrolledSubjectsDataDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.exception.SemesterNotFoundException
import ar.edu.unq.pds03backend.exception.UserNotFoundException
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.Semester
import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.model.Student
import ar.edu.unq.pds03backend.repository.IUserRepository
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.service.IUserService
import ar.edu.unq.pds03backend.utils.SemesterHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(
    @Autowired private val userRepository: IUserRepository,
    @Autowired private val quoteRequestRepository: IQuoteRequestRepository,
    @Autowired private val semesterRepository: ISemesterRepository,
) : IUserService {

    override fun findByEmailAndDni(email: String, dni: String): Optional<User> =
        userRepository.findByEmailAndDni(email, dni)

    override fun getById(id: Long): UserResponseDTO {
        val user = getUser(id)
        return getUserInfo(user)
    }

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
                quoteRequestRepository.findAllByStudentIdAndCourseSemesterId(student.id!!, currentSemester.id!!, getSortByCreatedOnAsc()).map {
                    RequestedSubjectsDTO.Mapper(it).map()
                }
        }
        return UserResponseDTO(
            id = user.id!!,
            isStudent = user.isStudent(),
            username = user.username,
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

    private fun getUser(id: Long): User {
        val maybeUser = userRepository.findById(id)
        if (!maybeUser.isPresent) throw UserNotFoundException()
        return maybeUser.get()
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