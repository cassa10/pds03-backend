package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.quoteRequest.AdminCommentRequestDTO
import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesAndSubjectsResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesInfoResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithRequestedQuotesResponseDTO
import ar.edu.unq.pds03backend.service.IQuoteRequestService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.Optional
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/quoteRequest")
@Validated
class QuoteRequestController(@Autowired private val quoteRequestService: IQuoteRequestService) {

    @PostMapping
    @LogExecution
    fun create(@Valid @RequestBody quoteRequestRequestDTO: QuoteRequestRequestDTO): String {
        quoteRequestService.create(quoteRequestRequestDTO)
        return "quote request created"
    }

    @GetMapping("/{id}")
    @LogExecution
    fun getById(@PathVariable @Valid id: Long): QuoteRequestResponseDTO = quoteRequestService.getById(id)

    @GetMapping
    @LogExecution
    fun getAll(@RequestParam idCourse: Optional<Long>, @RequestParam idStudent: Optional<Long>): List<QuoteRequestResponseDTO> {
        return when {
            idCourse.isPresent && idStudent.isPresent -> quoteRequestService.getAllByCourseAndStudent(idCourse.get(), idStudent.get())
            idCourse.isPresent -> quoteRequestService.getAllByCourse(idCourse.get())
            idStudent.isPresent -> quoteRequestService.getAllCurrentSemesterByStudent(idStudent.get())
            else -> quoteRequestService.getAll()
        }
    }

    @GetMapping("/pending/courses")
    @LogExecution
    fun getQuoteRequestSubjectsPending(): List<QuoteRequestSubjectPendingResponseDTO> {
        return quoteRequestService.getQuoteRequestSubjectsPending()
    }

    @PutMapping("/{id}/adminComment")
    @LogExecution
    fun addAdminComment(@PathVariable @Valid id: Long, @Valid @RequestBody adminCommentRequestDTO: AdminCommentRequestDTO): String {
        quoteRequestService.addAdminComment(id, adminCommentRequestDTO)
        return "admin comment added"
    }

    @GetMapping("/pending/students")
    @LogExecution
    fun findAllStudentsWithQuoteStatusPending(): List<StudentWithQuotesInfoResponseDTO> {
        return quoteRequestService.findAllStudentsWithQuoteStatusPendingCurrentSemester()
    }

    @GetMapping("/pending/students/subject/{idSubject}")
    @LogExecution
    fun findAllStudentsWithQuoteStatusPendingToSubject(@PathVariable @Valid idSubject: Long): List<StudentWithQuotesAndSubjectsResponseDTO> {
        return quoteRequestService.findAllStudentsWithQuoteStatusPendingToSubjectCurrentSemester(idSubject)
    }

    @GetMapping("/pending/student/{idStudent}")
    @LogExecution
    fun findStudentWithPendingQuoteRequests(@PathVariable @Valid idStudent: Long): StudentWithRequestedQuotesResponseDTO {
        return quoteRequestService.findStudentWithPendingQuoteRequests(idStudent)
    }

    @DeleteMapping("/{id}")
    @LogExecution
    fun delete(@PathVariable @Valid id: Long): String {
        quoteRequestService.delete(id)
        return "quote request deleted"
    }

    @PutMapping("/{id}/accept")
    @LogExecution
    fun acceptQuoteRequest(@PathVariable @Valid id: Long): String {
        quoteRequestService.acceptQuoteRequest(id)
        return "quote request accepted successfully"
    }

    @PutMapping("/{id}/revoke")
    @LogExecution
    fun revokeQuoteRequest(@PathVariable @Valid id: Long): String {
        quoteRequestService.revokeQuoteRequest(id)
        return "quote request revoked successfully"
    }

    @PutMapping("/{id}/rollback")
    @LogExecution
    fun rollbackToPendingQuoteRequest(@PathVariable @Valid id: Long): String {
        quoteRequestService.rollbackToPendingRequest(id)
        return "quote request rollback to pending successfully"
    }
}
