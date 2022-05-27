package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.degree.SimpleDegreeResponseDTO
import ar.edu.unq.pds03backend.dto.user.RequestedSubjectsDTO
import ar.edu.unq.pds03backend.dto.user.SimpleEnrolledSubjectsDataDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.exception.UserNotFoundException
import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.model.Student
import ar.edu.unq.pds03backend.repository.IUserRepository
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(
    @Autowired private val userRepository: IUserRepository,
    @Autowired private val quoteRequestRepository: IQuoteRequestRepository,
) : IUserService {

    override fun findByEmailAndDni(email: String, dni: String): Optional<User> =
        userRepository.findByEmailAndDni(email, dni)

    override fun getById(id: Long): UserResponseDTO {
        val user = getUser(id)
        return getUserInfo(user)
    }

    private fun getUserInfo(user: User): UserResponseDTO {
        var enrolledSubjects = listOf<SimpleEnrolledSubjectsDataDTO>()
        var requestedSubjects = listOf<RequestedSubjectsDTO>()
        var enrolledDegrees = listOf<SimpleDegreeResponseDTO>()
        var legajo = ""
        if (user.isStudent()) {
            val student = user as Student
            legajo = student.legajo
            enrolledDegrees = student.enrolledDegrees.map {
                SimpleDegreeResponseDTO.Mapper(it.id!!, it.name, it.acronym).map()
            }
            enrolledSubjects = student.enrolledCourses.map {
                SimpleEnrolledSubjectsDataDTO.Mapper(it).map()
            }
            requestedSubjects = quoteRequestRepository.findAllByStudentId(student.id!!).map {
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
}