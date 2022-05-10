package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.service.impl.QuoteRequestService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/quoteRequest")
class QuoteRequestController(@Autowired private val quoteRequestService: QuoteRequestService) {

    @PostMapping("/{code}")
    @LogExecution
    fun create(@PathVariable code: String, @RequestBody quoteRequestRequestDTO: QuoteRequestRequestDTO): String {
        quoteRequestService.create(code, quoteRequestRequestDTO)
        return "quote request created"
    }

    @GetMapping
    @LogExecution
    fun getAllByCourse(@RequestParam code: String): List<QuoteRequestResponseDTO> = quoteRequestService.getAllByCourse(code)
}
