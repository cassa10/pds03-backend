package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.degree.DegreeRequestDTO
import ar.edu.unq.pds03backend.dto.degree.DegreeResponseDTO
import ar.edu.unq.pds03backend.service.impl.DegreeService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/degrees")
class DegreeController(@Autowired private val degreeService: DegreeService) {

    @PostMapping
    @LogExecution
    fun create(@RequestBody createDegreeRequestDTO: DegreeRequestDTO): String {
        degreeService.create(createDegreeRequestDTO)
        return "Degree created"
    }

    @GetMapping("/{id}")
    @LogExecution
    fun getById(@PathVariable id: Long): DegreeResponseDTO = degreeService.getById(id)

    @GetMapping
    @LogExecution
    fun getAll(): List<DegreeResponseDTO> = degreeService.getAll()

    @PutMapping("/{id}")
    @LogExecution
    fun update(@PathVariable id: Long, @RequestBody degreeRequestDTO: DegreeRequestDTO): String {
        degreeService.update(id, degreeRequestDTO)
        return "Degree updated"
    }

    @DeleteMapping("/{id}")
    @LogExecution
    fun delete(@PathVariable id: Long): String {
        degreeService.delete(id)
        return "Degree deleted"
    }
}
