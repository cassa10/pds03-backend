package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.degree.DegreeResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.service.impl.QuoteRequestService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@RequestMapping("/api/v1/quoteRequest")
class QuoteRequestController(@Autowired private val quoteRequestService: QuoteRequestService) {

    @PostMapping("/{code}")
    @LogExecution
    fun create(@PathVariable code: String, @RequestBody quoteRequestRequestDTO: QuoteRequestRequestDTO): String {
        quoteRequestService.create(code, quoteRequestRequestDTO)
        return "quote request created"
    }

    @GetMapping("/{id}")
    @LogExecution
    fun getById(@PathVariable id: Long): QuoteRequestResponseDTO = quoteRequestService.getById(id)

    @GetMapping
    @LogExecution
    fun getAll(@RequestParam code: Optional<String>, @RequestParam idStudent: Optional<Long>): List<QuoteRequestResponseDTO> {
        if (code.isPresent && idStudent.isPresent)
            return quoteRequestService.getAllByCourseAndStudent(code.get(), idStudent.get())

        if (code.isPresent)
            return quoteRequestService.getAllByCourse(code.get())

        if (idStudent.isPresent)
            return quoteRequestService.getAllByStudent(idStudent.get())

        return quoteRequestService.getAll()
    }
}
