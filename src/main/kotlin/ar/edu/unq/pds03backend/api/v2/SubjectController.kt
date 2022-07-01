package ar.edu.unq.pds03backend.api.v2

import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.service.ISubjectService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController(value = "V2SubjectController")
@RequestMapping("/api/v2/subjects")
@Validated
class SubjectController(@Autowired private val subjectService: ISubjectService) {

    @GetMapping
    @LogExecution
    fun getAll(pageable: Pageable): Page<SubjectResponseDTO> = subjectService.getAll(pageable)
}
