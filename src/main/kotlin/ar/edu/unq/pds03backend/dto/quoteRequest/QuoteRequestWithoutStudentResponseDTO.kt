package ar.edu.unq.pds03backend.dto.quoteRequest

import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.model.Warning
import java.time.LocalDateTime

data class QuoteRequestWithoutStudentResponseDTO(
    val id: Long,
    val course: SimpleCourseForSubjectWithQuoteInfoDTO,
    val subject: SubjectResponseDTO,
    val state: QuoteState,
    val comment: String,
    val adminComment: String,
    val createdOn: LocalDateTime,
    val warnings: List<Warning>
)

data class SimpleCourseForSubjectWithQuoteInfoDTO(
    val id: Long,
    val name: String,
    val availableQuotes: Int,
    val requestedQuotes: Int,
)