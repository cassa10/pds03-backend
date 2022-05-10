package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.degree.DegreeRequestDTO
import ar.edu.unq.pds03backend.dto.degree.DegreeResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO

interface IQuoteRequestService {
    fun create(code: String, quoteRequestRequestDTO: QuoteRequestRequestDTO)
}