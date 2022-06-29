package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.AdminCommentRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestWithWarningsResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesAndSubjectsResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesInfoResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithRequestedQuotesResponseDTO
import ar.edu.unq.pds03backend.model.QuoteState
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IQuoteRequestService {
    fun create(quoteRequestRequestDTO: QuoteRequestRequestDTO)
    fun getById(id: Long): QuoteRequestWithWarningsResponseDTO
    fun getAll(quoteStates: Set<QuoteState>): List<QuoteRequestResponseDTO>
    fun getAll(quoteStates: Set<QuoteState>, pageable: Pageable): Page<QuoteRequestResponseDTO>
    fun getAllByCourseAndStudent(idCourse: Long, idStudent: Long, quoteStates: Set<QuoteState>): List<QuoteRequestResponseDTO>
    fun getAllByCourseAndStudent(idCourse: Long, idStudent: Long, quoteStates: Set<QuoteState>, pageable: Pageable): Page<QuoteRequestResponseDTO>
    fun getAllByCourse(idCourse: Long, quoteStates: Set<QuoteState>): List<QuoteRequestResponseDTO>
    fun getAllByCourse(idCourse: Long, quoteStates: Set<QuoteState>, pageable: Pageable): Page<QuoteRequestResponseDTO>
    fun getAllCurrentSemesterByStudent(idStudent: Long, quoteStates: Set<QuoteState>): List<QuoteRequestResponseDTO>
    fun getAllCurrentSemesterByStudent(idStudent: Long, quoteStates: Set<QuoteState>, pageable: Pageable): Page<QuoteRequestResponseDTO>
    fun getQuoteRequestSubjects(states: Set<QuoteState>): List<QuoteRequestSubjectPendingResponseDTO>
    fun getQuoteRequestSubjects(states: Set<QuoteState>, pageable: Pageable): Page<QuoteRequestSubjectPendingResponseDTO>
    fun addAdminComment(idQuoteRequest: Long, adminCommentRequestDTO: AdminCommentRequestDTO)
    fun findAllStudentsWithQuoteRequestCurrentSemester(states: Set<QuoteState>): List<StudentWithQuotesInfoResponseDTO>
    fun findAllStudentsWithQuoteRequestInSubjectCurrentSemester(idSubject: Long, states: Set<QuoteState>): List<StudentWithQuotesAndSubjectsResponseDTO>
    fun delete(id: Long)
    fun findStudentWithQuoteRequests(idStudent: Long, states: Set<QuoteState>): StudentWithRequestedQuotesResponseDTO
    fun acceptQuoteRequest(id: Long)
    fun revokeQuoteRequest(id: Long)
    fun rollbackToPendingRequest(id: Long)
}
