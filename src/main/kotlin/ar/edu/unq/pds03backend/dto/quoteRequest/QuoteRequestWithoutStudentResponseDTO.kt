package ar.edu.unq.pds03backend.dto.quoteRequest

import ar.edu.unq.pds03backend.dto.course.SimpleCourseForSubjectDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.model.QuoteState

data class QuoteRequestWithoutStudentResponseDTO(
    val id: Long,
    val course: SimpleCourseForSubjectDTO,
    val subject: SubjectResponseDTO,
    val state: QuoteState,
    val comment: String,
    val adminComment: String
)