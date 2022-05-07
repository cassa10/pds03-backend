package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.degree.CreateDegreeRequestDTO
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
    fun create(@RequestBody createDegreeRequestDTO: CreateDegreeRequestDTO): String {
        degreeService.create(createDegreeRequestDTO)
        return "Degree created"
    }

    @GetMapping
    @LogExecution
    fun getAll(): List<DegreeResponseDTO> = degreeService.getAll()

    @PutMapping("/{id}")
    @LogExecution
    fun update(@PathVariable id: Long, @RequestBody createDegreeRequestDTO: CreateDegreeRequestDTO): String {
        degreeService.update(id, createDegreeRequestDTO)
        return "Degree updated"
    }

    @DeleteMapping("/{id}")
    @LogExecution
    fun delete(@PathVariable id: Long): String {
        degreeService.delete(id)
        return "Degree deleted"
    }
}
