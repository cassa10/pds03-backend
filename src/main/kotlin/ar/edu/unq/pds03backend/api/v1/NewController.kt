package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.GenericResponse
import ar.edu.unq.pds03backend.dto.news.NewRequestDTO
import ar.edu.unq.pds03backend.model.New
import ar.edu.unq.pds03backend.service.INewService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/news")
@Validated
class NewController(@Autowired val newService: INewService) {

    @PostMapping
    @LogExecution
    fun create(@Valid @RequestBody newReqDTO: NewRequestDTO): GenericResponse {
        newService.create(newReqDTO)
        return GenericResponse(HttpStatus.OK, "new created")
    }

    @GetMapping("/{id}")
    @LogExecution
    fun getById(@PathVariable @Valid id: Long): New = newService.get(id)

    @GetMapping
    @LogExecution
    fun getAll(): List<New> = newService.getAll()

    @PutMapping("/{id}")
    @LogExecution
    fun update(@PathVariable @Valid id: Long, @Valid @RequestBody newReqDTO: NewRequestDTO): GenericResponse {
        newService.update(id, newReqDTO)
        return GenericResponse(HttpStatus.OK,"new updated")
    }


    @DeleteMapping("/{id}")
    @LogExecution
    fun delete(@PathVariable @Valid id: Long): GenericResponse {
        newService.delete(id)
        return GenericResponse(HttpStatus.OK, "new deleted")
    }
}