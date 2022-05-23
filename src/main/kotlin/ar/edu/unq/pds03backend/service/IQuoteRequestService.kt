package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.AdminCommentRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesInfoResponseDTO

interface IQuoteRequestService {
    fun create(quoteRequestRequestDTO: QuoteRequestRequestDTO)
    fun getById(id: Long): QuoteRequestResponseDTO
    fun getAll(): List<QuoteRequestResponseDTO>
    fun getAllByCourseAndStudent(idCourse: Long, idStudent: Long): List<QuoteRequestResponseDTO>
    fun getAllByCourse(idCourse: Long): List<QuoteRequestResponseDTO>
    fun getAllByStudent(idStudent: Long): List<QuoteRequestResponseDTO>
    fun getQuoteRequestSubjectsPending(): List<QuoteRequestSubjectPendingResponseDTO>
    fun addAdminComment(idQuoteRequest: Long, adminCommentRequestDTO: AdminCommentRequestDTO)
    fun findAllStudentsWithQuoteStatusPendingCurrentSemester(): List<StudentWithQuotesInfoResponseDTO>
    fun delete(id: Long)
}
