package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.GenericResponse
import ar.edu.unq.pds03backend.dto.quoteRequest.AdminCommentRequestDTO
import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.dto.degree.DegreeStatisticsResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestWithWarningsResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesAndSubjectsResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesInfoResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithRequestedQuotesResponseDTO
import ar.edu.unq.pds03backend.exception.InvalidQuoteStateRequestException
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.service.IQuoteRequestService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
    fun create(@Valid @RequestBody quoteRequestRequestDTO: QuoteRequestRequestDTO): GenericResponse {
        quoteRequestService.create(quoteRequestRequestDTO)
        return GenericResponse(HttpStatus.OK, "quote request/s created")
    }

    @GetMapping("/{id}")
    @LogExecution
    fun getById(@PathVariable @Valid id: Long): QuoteRequestWithWarningsResponseDTO = quoteRequestService.getById(id)

    @GetMapping("/states")
    fun getStates(): Array<QuoteState> = QuoteState.values()

    @GetMapping
    @LogExecution
    fun getAll(@RequestParam idCourse: Optional<Long>, @RequestParam idStudent: Optional<Long>, @RequestParam states: Optional<String>): List<QuoteRequestResponseDTO> {
        val queryStates = getQueryQuoteStates(states)
        return when {
            idCourse.isPresent && idStudent.isPresent -> quoteRequestService.getAllByCourseAndStudent(idCourse.get(), idStudent.get(), queryStates)
            idCourse.isPresent -> quoteRequestService.getAllByCourse(idCourse.get(), queryStates)
            idStudent.isPresent -> quoteRequestService.getAllCurrentSemesterByStudent(idStudent.get(), queryStates)
            else -> quoteRequestService.getAll(queryStates)
        }
    }

    @PutMapping("/{id}/adminComment")
    @LogExecution
    fun addAdminComment(@PathVariable @Valid id: Long, @Valid @RequestBody adminCommentRequestDTO: AdminCommentRequestDTO): GenericResponse {
        quoteRequestService.addAdminComment(id, adminCommentRequestDTO)
        return GenericResponse(HttpStatus.OK, "admin comment added")
    }

    @GetMapping("/courses")
    @LogExecution
    fun getQuoteRequestSubjects(@RequestParam states: Optional<String>): List<QuoteRequestSubjectPendingResponseDTO> =
        quoteRequestService.getQuoteRequestSubjects(getQueryQuoteStates(states))

    @GetMapping("/students")
    @LogExecution
    fun findAllStudentsWithQuoteRequest(@RequestParam states: Optional<String>): List<StudentWithQuotesInfoResponseDTO> =
        quoteRequestService.findAllStudentsWithQuoteRequestCurrentSemester(getQueryQuoteStates(states))

    @GetMapping("/students/subject/{idSubject}")
    @LogExecution
    fun findAllStudentsWithQuoteRequestInSubject(@PathVariable @Valid idSubject: Long, @RequestParam states: Optional<String>): List<StudentWithQuotesAndSubjectsResponseDTO> =
        quoteRequestService.findAllStudentsWithQuoteRequestInSubjectCurrentSemester(idSubject, getQueryQuoteStates(states))

    @GetMapping("/student/{idStudent}")
    @LogExecution
    fun findStudentWithQuoteRequest(@PathVariable @Valid idStudent: Long, @RequestParam states: Optional<String>): StudentWithRequestedQuotesResponseDTO =
        quoteRequestService.findStudentWithQuoteRequests(idStudent, getQueryQuoteStates(states))

    @DeleteMapping("/{id}")
    @LogExecution
    fun delete(@PathVariable @Valid id: Long): GenericResponse {
        quoteRequestService.delete(id)
        return GenericResponse(HttpStatus.OK, "quote request deleted")
    }

    @PutMapping("/{id}/accept")
    @LogExecution
    fun acceptQuoteRequest(@PathVariable @Valid id: Long): GenericResponse {
        quoteRequestService.acceptQuoteRequest(id)
        return GenericResponse(HttpStatus.OK, "quote request accepted successfully")
    }

    @PutMapping("/{id}/revoke")
    @LogExecution
    fun revokeQuoteRequest(@PathVariable @Valid id: Long): GenericResponse {
        quoteRequestService.revokeQuoteRequest(id)
        return GenericResponse(HttpStatus.OK, "quote request revoked successfully")
    }

    @PutMapping("/{id}/rollback")
    @LogExecution
    fun rollbackToPendingQuoteRequest(@PathVariable @Valid id: Long): GenericResponse {
        quoteRequestService.rollbackToPendingRequest(id)
        return GenericResponse(HttpStatus.OK, "quote request rollback to pending successfully")
    }

    @GetMapping("/statistics")
    fun getStatistics(@RequestParam idStudent: Optional<Long>): List<DegreeStatisticsResponseDTO> {
        return when {
            idStudent.isPresent -> quoteRequestService.getStatisticsByStudentId(idStudent.get())
                else -> quoteRequestService.getAllStatistics()
        }
    }

    private fun getQueryQuoteStates(states: Optional<String>): Set<QuoteState> {
        if(states.isPresent.not()) return QuoteState.values().toSet()
        try{
            val statesStr = states.get().split(",")
            return statesStr.map { QuoteState.valueOf(it) }.toSet()
        }catch(e: Exception){
            throw InvalidQuoteStateRequestException()
        }
    }
}
