package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.user.RequestedSubjectsDTO
import ar.edu.unq.pds03backend.dto.user.SimpleEnrolledSubjectsDataDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.exception.UserNotFoundException
import ar.edu.unq.pds03backend.model.Person
import ar.edu.unq.pds03backend.model.Student
import ar.edu.unq.pds03backend.repository.IPersonRepository
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.service.IPersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class PersonService(
    @Autowired private val personRepository: IPersonRepository,
    @Autowired private val quoteRequestRepository: IQuoteRequestRepository,
) : IPersonService {

    override fun findByEmailAndDni(email: String, dni: String): Optional<Person> =
        personRepository.findByEmailAndDni(email, dni)

    override fun getById(id: Long): UserResponseDTO {
        val person = getPerson(id)
        return getUserInfo(person)
    }

    private fun getUserInfo(person: Person): UserResponseDTO {
        var enrolledSubjects = listOf<SimpleEnrolledSubjectsDataDTO>()
        var requestedSubjects = listOf<RequestedSubjectsDTO>()
        var legajo = ""
        //TODO: Validate isStudent works
        if (person.isStudent()) {
            val student = person as Student
            legajo = student.legajo
            enrolledSubjects = student.enrolledCourses.map {
                SimpleEnrolledSubjectsDataDTO.Mapper(it).map()
            }
            requestedSubjects = quoteRequestRepository.findAllByStudentId(student.id!!).map {
                RequestedSubjectsDTO.Mapper(it).map()
            }
        }
        return UserResponseDTO(
            id = person.id!!,
            username = person.user.username,
            firstName = person.firstName,
            lastName = person.lastName,
            dni = person.dni,
            email = person.email,
            legajo = legajo,
            enrolledSubjects = enrolledSubjects,
            requestedSubjects = requestedSubjects,
        )
    }

    private fun getPerson(id: Long): Person {
        val person = personRepository.findById(id)
        if (!person.isPresent) throw UserNotFoundException()
        return person.get()
    }
}