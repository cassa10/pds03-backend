package ar.edu.unq.pds03backend.api.v1


import ar.edu.unq.pds03backend.dto.GenericResponse
import ar.edu.unq.pds03backend.dto.subject.SubjectRequestDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectWithCoursesResponseDTO
import ar.edu.unq.pds03backend.service.ISubjectService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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
    fun create(@Valid @RequestBody subjectRequestDTO: SubjectRequestDTO): GenericResponse {
        subjectService.create(subjectRequestDTO)
        return GenericResponse(HttpStatus.OK, "subject created")
    }

    @PutMapping("/{id}")
    @LogExecution
    fun update(@PathVariable @Valid id: Long, @Valid @RequestBody subjectRequestDTO: SubjectRequestDTO): GenericResponse {
        subjectService.update(id, subjectRequestDTO)
        return GenericResponse(HttpStatus.OK, "subject updated")
    }

    @DeleteMapping("/{id}")
    @LogExecution
    fun delete(@PathVariable @Valid id: Long): GenericResponse {
        subjectService.delete(id)
        return GenericResponse(HttpStatus.OK, "subject deleted")
    }

    @GetMapping("/currents")
    @LogExecution
    fun getAllCurrent(@RequestParam idDegree: Optional<Long>, @RequestParam idStudent: Optional<Long>): List<SubjectWithCoursesResponseDTO> {
        return when {
            idDegree.isPresent && idStudent.isPresent -> {
                //TODO: Refactor in JPQL query
                val subjectsByDegree = subjectService.getAllCurrentByDegree(idDegree.get())
                val subjectsByStudent = subjectService.getAllCurrentByStudent(idStudent.get())
                return subjectsByStudent.intersect(subjectsByDegree).toList()
            }
            idDegree.isPresent -> subjectService.getAllCurrentByDegree(idDegree.get())
            idStudent.isPresent -> subjectService.getAllCurrentByStudent(idStudent.get())
            else -> subjectService.getAllCurrent()
        }
    }

    @PostMapping("/subjects/import")
    @LogExecution
    fun importSubjectsCsv(@RequestParam("file") file: MultipartFile): GenericResponse {
        //TODO: Add Parser Csv and persistence
        return GenericResponse(HttpStatus.OK,"imported successfully")
    }
}