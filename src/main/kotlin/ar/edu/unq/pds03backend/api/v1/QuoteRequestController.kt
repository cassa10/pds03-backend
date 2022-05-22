package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
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
            idStudent.isPresent -> quoteRequestService.getAllByStudent(idStudent.get())
            else -> quoteRequestService.getAll()
        }
    }

    @GetMapping("/courses/pending")
    @LogExecution
    fun getQuoteRequestSubjectsPending(): List<QuoteRequestSubjectPendingResponseDTO> {
        return quoteRequestService.getQuoteRequestSubjectsPending()
    }
}
