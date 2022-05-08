package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO

interface IQuoteRequestService {
    fun create(code: String, quoteRequestRequestDTO: QuoteRequestRequestDTO)
    fun getAllByCourse(code: String): List<QuoteRequestResponseDTO>
}