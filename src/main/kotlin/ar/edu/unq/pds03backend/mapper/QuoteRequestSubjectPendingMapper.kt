package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository

class QuoteRequestSubjectPendingMapper(
        private val quoteRequestRepository: IQuoteRequestRepository
) {
    fun map(quoteRequestSubjectsPending: QuoteRequest) : QuoteRequestSubjectPendingResponseDTO {
        with(quoteRequestSubjectsPending.course) {
            return QuoteRequestSubjectPendingResponseDTO(
                    idSubject = subject.id!!,
                    name = subject.name,
                    course = CourseResponseDTOMapper.map(this),
                    availableQuotes = total_quotes - quoteRequestRepository.countByStateAndCourseId(QuoteState.APPROVED, id!!),
                    requestQuotes = quoteRequestRepository.countByStateAndCourseId(QuoteState.PENDING, id!!)
            )
        }
    }
}
