package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.AdminCommentRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesAndSubjectsResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesInfoResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithRequestedQuotesResponseDTO

interface IQuoteRequestService {
    fun create(quoteRequestRequestDTO: QuoteRequestRequestDTO)
    fun getById(id: Long): QuoteRequestResponseDTO
    fun getAll(): List<QuoteRequestResponseDTO>
    fun getAllByCourseAndStudent(idCourse: Long, idStudent: Long): List<QuoteRequestResponseDTO>
    fun getAllByCourse(idCourse: Long): List<QuoteRequestResponseDTO>
    fun getAllCurrentSemesterByStudent(idStudent: Long): List<QuoteRequestResponseDTO>
    fun getQuoteRequestSubjectsPending(): List<QuoteRequestSubjectPendingResponseDTO>
    fun addAdminComment(idQuoteRequest: Long, adminCommentRequestDTO: AdminCommentRequestDTO)
    fun findAllStudentsWithQuoteStatusPendingCurrentSemester(): List<StudentWithQuotesInfoResponseDTO>
    fun findAllStudentsWithQuoteStatusPendingToSubjectCurrentSemester(idSubject: Long): List<StudentWithQuotesAndSubjectsResponseDTO>
    fun delete(id: Long)
    fun findStudentWithPendingQuoteRequests(idStudent: Long): StudentWithRequestedQuotesResponseDTO
    fun acceptQuoteRequest(id: Long)
}
