package ar.edu.unq.pds03backend.dto.student

import ar.edu.unq.pds03backend.dto.course.CourseWithSubjectInfoResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestWithoutStudentResponseDTO
import ar.edu.unq.pds03backend.dto.degree.EnrolledDegreeResponseDTO
import ar.edu.unq.pds03backend.mapper.QuoteRequestMapper
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.Student
import ar.edu.unq.pds03backend.model.Warning

data class StudentResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String,
    val maxCoefficient: Float,
    val enrolledDegrees: List<EnrolledDegreeResponseDTO>,
)

class StudentWithQuotesInfoResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String,
    val requested_quotes: Int,
    val maxCoefficient: Float,
    val enrolledDegrees: List<EnrolledDegreeResponseDTO>,
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
                requested_quotes = numberOfPendingQuoteRequest,
                maxCoefficient = student.maxCoefficient(),
                enrolledDegrees = student.enrolledDegrees.map { EnrolledDegreeResponseDTO.Mapper(it, student.getStudiedDegreeCoefficient(it), student.getStudiedDegreeByDegree(it)).map() },
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
    val maxCoefficient: Float,
    val enrolledDegrees: List<EnrolledDegreeResponseDTO>,
    val enrolledCourses: List<CourseWithSubjectInfoResponseDTO>,
    val quoteRequests: List<QuoteRequestWithoutStudentResponseDTO>,
) {
    class Mapper(private val student: Student, private val quoteRequestsWithWarnings: List<Pair<QuoteRequest, List<Warning>>>) {
        fun map(): StudentWithRequestedQuotesResponseDTO =
            StudentWithRequestedQuotesResponseDTO(
                id = student.id!!,
                firstName = student.firstName,
                lastName = student.lastName,
                dni = student.dni,
                email = student.email,
                legajo = student.legajo,
                maxCoefficient = student.maxCoefficient(),
                enrolledDegrees = student.enrolledDegrees.map { EnrolledDegreeResponseDTO.Mapper(it, student.getStudiedDegreeCoefficient(it), student.getStudiedDegreeByDegree(it)).map() },
                enrolledCourses = student.enrolledCourses.map {
                    CourseWithSubjectInfoResponseDTO.Mapper(it).map()
                },
                quoteRequests = quoteRequestsWithWarnings.map {
                    QuoteRequestMapper.toQuoteRequestWithoutStudentResponseDTO(it.first, it.second)
                },
            )
    }
}