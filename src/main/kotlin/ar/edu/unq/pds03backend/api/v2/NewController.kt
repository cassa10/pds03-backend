package ar.edu.unq.pds03backend.api.v2

import ar.edu.unq.pds03backend.model.New
import ar.edu.unq.pds03backend.service.INewService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController(value = "V2NewController")
@RequestMapping("/api/v2/news")
@Validated
class NewController(@Autowired val newService: INewService) {

    @GetMapping
    @LogExecution
    fun getAll(pageable: Pageable): Page<New> = newService.getAll(pageable)
}