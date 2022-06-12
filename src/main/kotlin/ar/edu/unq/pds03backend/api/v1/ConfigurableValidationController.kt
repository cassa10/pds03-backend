package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.service.IConfigurableValidationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/validation_config")
class ConfigurableValidationController(
    @Autowired private val configurableValidationService: IConfigurableValidationService
) {
    //TODO: Pass JWT and only allow DIRECTOR rol
    @GetMapping
    fun getAll() = configurableValidationService.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long) = configurableValidationService.getById(id)

    @PutMapping("/{id}/{active}")
    fun update(@PathVariable("id") id: Long, @PathVariable("active") active: Boolean) = configurableValidationService.update(id, active)
}