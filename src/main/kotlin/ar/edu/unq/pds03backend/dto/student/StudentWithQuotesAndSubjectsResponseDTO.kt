package ar.edu.unq.pds03backend.dto.student

import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestWithoutStudentResponseDTO
import ar.edu.unq.pds03backend.dto.degree.EnrolledDegreeResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectWithCoursesResponseDTO

data class StudentWithQuotesAndSubjectsResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String,
    val maxCoefficient: Float,
    val enrolledDegrees: List<EnrolledDegreeResponseDTO>,
    val enrolledSubjects: List<SubjectWithCoursesResponseDTO>,
    val quoteRequests: List<QuoteRequestWithoutStudentResponseDTO>
)