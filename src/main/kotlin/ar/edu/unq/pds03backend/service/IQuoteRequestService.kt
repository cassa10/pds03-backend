package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO

interface IQuoteRequestService {
    fun create(code: String, quoteRequestRequestDTO: QuoteRequestRequestDTO)
    fun getById(id: Long): QuoteRequestResponseDTO
    fun getAll(): List<QuoteRequestResponseDTO>
    fun getAllByCourseAndStudent(code: String, idStudent: Long): List<QuoteRequestResponseDTO>
    fun getAllByCourse(code: String): List<QuoteRequestResponseDTO>
    fun getAllByStudent(idStudent: Long): List<QuoteRequestResponseDTO>
}