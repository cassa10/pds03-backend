package ar.edu.unq.pds03backend.dto.student

import ar.edu.unq.pds03backend.dto.course.CourseWithSubjectInfoResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestWithoutStudentResponseDTO
import ar.edu.unq.pds03backend.mapper.QuoteRequestMapper
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.Student

data class StudentResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String,
    val username: String,
)

class StudentWithQuotesInfoResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String,
    val username: String,
    val requested_quotes: Int,
) {
    class Mapper(private val student: Student, private val numberOfPendingQuoteRequest: Int) {
        fun map(): StudentWithQuotesInfoResponseDTO =
            StudentWithQuotesInfoResponseDTO(
                id = student.id!!,
                firstName = student.firstName,
                lastName = student.lastName,
                dni = student.dni,
                email = student.email,
                legajo = student.legajo,
                username = student.username,
                requested_quotes = numberOfPendingQuoteRequest,
            )
    }
}

class StudentWithRequestedQuotesResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String,
    val username: String,
    val enrolledCourses: List<CourseWithSubjectInfoResponseDTO>,
    val quoteRequests: List<QuoteRequestWithoutStudentResponseDTO>,
) {
    class Mapper(private val student: Student, private val quoteRequests: List<QuoteRequest>) {
        fun map(): StudentWithRequestedQuotesResponseDTO =
            StudentWithRequestedQuotesResponseDTO(
                id = student.id!!,
                firstName = student.firstName,
                lastName = student.lastName,
                dni = student.dni,
                email = student.email,
                legajo = student.legajo,
                username = student.username,
                enrolledCourses = student.enrolledCourses.map {
                    CourseWithSubjectInfoResponseDTO.Mapper(it).map()
                },
                quoteRequests = quoteRequests.map {
                    QuoteRequestMapper.toQuoteRequestWithoutStudentResponseDTO(it)
                },
            )
    }
}