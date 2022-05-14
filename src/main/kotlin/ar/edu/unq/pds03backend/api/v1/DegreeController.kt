package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.degree.DegreeRequestDTO
import ar.edu.unq.pds03backend.dto.degree.DegreeResponseDTO
import ar.edu.unq.pds03backend.service.IDegreeService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/degrees")
@Validated
class DegreeController(@Autowired private val degreeService: IDegreeService) {

    @PostMapping
    @LogExecution
    fun create(@Valid @RequestBody createDegreeRequestDTO: DegreeRequestDTO): String {
        degreeService.create(createDegreeRequestDTO)
        return "degree created"
    }

    @GetMapping("/{id}")
    @LogExecution
    fun getById(@PathVariable @Valid id: Long): DegreeResponseDTO = degreeService.getById(id)

    @GetMapping
    @LogExecution
    fun getAll(): List<DegreeResponseDTO> = degreeService.getAll()

    @PutMapping("/{id}")
    @LogExecution
    fun update(@PathVariable @Valid id: Long, @Valid @RequestBody degreeRequestDTO: DegreeRequestDTO): String {
        degreeService.update(id, degreeRequestDTO)
        return "degree updated"
    }

    /*
    TODO: BUG - Delete all objects, not by id
    @DeleteMapping("/{id}")
    @LogExecution
    fun delete(@PathVariable @Valid id: Long): String {
        degreeService.delete(id)
        return "degree deleted"
    }
    */
}
