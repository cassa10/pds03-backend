package ar.edu.unq.pds03backend.api.v1


import ar.edu.unq.pds03backend.dto.subject.SubjectRequestDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectWithCoursesResponseDTO
import ar.edu.unq.pds03backend.service.ISubjectService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/subjects")
@Validated
class SubjectController(@Autowired private val subjectService: ISubjectService) {

    @GetMapping("/{id}")
    @LogExecution
    fun getById(@PathVariable @Valid id: Long): SubjectResponseDTO = subjectService.getById(id)

    @GetMapping
    @LogExecution
    fun getAll(): List<SubjectResponseDTO> = subjectService.getAll()

    @PostMapping
    @LogExecution
    fun create(@Valid @RequestBody subjectRequestDTO: SubjectRequestDTO): String {
        subjectService.create(subjectRequestDTO)
        return "subject created"
    }

    @PutMapping("/{id}")
    @LogExecution
    fun update(@PathVariable @Valid id: Long, @Valid @RequestBody subjectRequestDTO: SubjectRequestDTO): String {
        subjectService.update(id, subjectRequestDTO)
        return "subject updated"
    }

    @DeleteMapping("/{id}")
    @LogExecution
    fun delete(@PathVariable @Valid id: Long): String {
        subjectService.delete(id)
        return "subject deleted"
    }

    @GetMapping("/currents")
    @LogExecution
    fun getAllCurrent(@RequestParam idDegree: Optional<Long>): List<SubjectWithCoursesResponseDTO> {
        return when {
            idDegree.isPresent -> subjectService.getAllCurrentByDegree(idDegree.get())
            else -> subjectService.getAllCurrent()
        }
    }
}