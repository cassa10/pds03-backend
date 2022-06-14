package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.model.QuoteRequest

class QuoteRequestSubjectMapper(
        private val approvedQuotes: Int,
        private val requestQuotes: Int,
) {
    fun map(quoteRequestSubjectsPending: QuoteRequest) : QuoteRequestSubjectPendingResponseDTO {
        with(quoteRequestSubjectsPending.course) {
            return QuoteRequestSubjectPendingResponseDTO(
                    idSubject = subject.id!!,
                    name = subject.name,
                    course = CourseResponseDTOMapper.map(this),
                    total_quotes = total_quotes,
                    availableQuotes = total_quotes - approvedQuotes,
                    requestQuotes = requestQuotes,
            )
        }
    }
}
