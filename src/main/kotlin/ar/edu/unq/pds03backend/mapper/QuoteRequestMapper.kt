package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestWithoutStudentResponseDTO
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.Warning

object QuoteRequestMapper : Mapper<QuoteRequest, QuoteRequestResponseDTO> {
    override fun toDTO(quoteRequest: QuoteRequest): QuoteRequestResponseDTO {
        return QuoteRequestResponseDTO(
            id = quoteRequest.id!!,
            course = CourseMapper.toSimpleCourseResponseDTO(quoteRequest.course),
            student = StudentMapper.toDTO(quoteRequest.student),
            state = quoteRequest.state,
            comment = quoteRequest.comment,
            createdOn = quoteRequest.createdOn,
        )
    }

    fun toQuoteRequestWithoutStudentResponseDTO(quoteRequest: QuoteRequest, availableQuotes: Int, requestedQuotes: Int, warnings: List<Warning>): QuoteRequestWithoutStudentResponseDTO =
        QuoteRequestWithoutStudentResponseDTO(
            id = quoteRequest.id!!,
            course = CourseMapper.toSimpleCourseForSubjectWithQuoteInfoDTO(quoteRequest.course, availableQuotes, requestedQuotes),
            subject = SubjectMapper.toDTO(quoteRequest.course.subject),
            state = quoteRequest.state,
            comment = quoteRequest.comment,
            adminComment = quoteRequest.adminComment,
            createdOn = quoteRequest.createdOn,
            warnings = warnings,
        )
}