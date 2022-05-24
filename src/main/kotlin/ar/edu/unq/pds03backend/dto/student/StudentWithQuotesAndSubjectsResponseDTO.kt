package ar.edu.unq.pds03backend.dto.student

import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestWithoutStudentResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectWithCoursesResponseDTO

data class StudentWithQuotesAndSubjectsResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String,
    val username: String,
    val enrolledSubjects: List<SubjectWithCoursesResponseDTO>,
    val quoteRequests: List<QuoteRequestWithoutStudentResponseDTO>
)