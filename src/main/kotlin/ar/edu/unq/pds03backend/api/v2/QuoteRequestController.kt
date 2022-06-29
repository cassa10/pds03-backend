package ar.edu.unq.pds03backend.api.v2

import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.exception.InvalidQuoteStateRequestException
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.service.IQuoteRequestService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController(value = "V2QuoteRequestController")
@RequestMapping("/api/v2/quoteRequest")
@Validated
class QuoteRequestController(@Autowired private val quoteRequestService: IQuoteRequestService) {

    @GetMapping
    @LogExecution
    fun getAll(@RequestParam idCourse: Optional<Long>, @RequestParam idStudent: Optional<Long>,
               @RequestParam states: Optional<String>, pageable: Pageable): Page<QuoteRequestResponseDTO> {
        val queryStates = getQueryQuoteStates(states)
        return when {
            idCourse.isPresent && idStudent.isPresent -> quoteRequestService.getAllByCourseAndStudent(idCourse.get(), idStudent.get(), queryStates, pageable)
            idCourse.isPresent -> quoteRequestService.getAllByCourse(idCourse.get(), queryStates, pageable)
            idStudent.isPresent -> quoteRequestService.getAllCurrentSemesterByStudent(idStudent.get(), queryStates, pageable)
            else -> quoteRequestService.getAll(queryStates, pageable)
        }
    }

    @GetMapping("/courses")
    @LogExecution
    fun getQuoteRequestSubjects(@RequestParam states: Optional<String>, pageable: Pageable): Page<QuoteRequestSubjectPendingResponseDTO> =
            quoteRequestService.getQuoteRequestSubjects(getQueryQuoteStates(states), pageable)

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
