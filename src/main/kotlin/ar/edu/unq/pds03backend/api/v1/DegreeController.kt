package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.CreateDegreeRequestDTO
import ar.edu.unq.pds03backend.service.impl.DegreeService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/degrees")
class DegreeController(@Autowired private val degreeService: DegreeService) {

    @PostMapping
    @LogExecution
    fun create(@RequestBody createDegreeRequestDTO: CreateDegreeRequestDTO): ResponseEntity<String> {
        degreeService.create(createDegreeRequestDTO)
        return ResponseEntity.ok("Degree created")
    }
}
