package ar.edu.unq.pds03backend.dto.student

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