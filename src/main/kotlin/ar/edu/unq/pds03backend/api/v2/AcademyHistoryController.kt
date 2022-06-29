package ar.edu.unq.pds03backend.api.v2

import ar.edu.unq.pds03backend.dto.academyHistory.StudiedDegreeDTO
import ar.edu.unq.pds03backend.service.IAcademyHistoryService
import ar.edu.unq.pds03backend.service.ICsvService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController(value = "V2AcademyHistoryController")
@RequestMapping("/api/v2/academy_history")
@Validated
class AcademyHistoryController(
        @Autowired private val csvService: ICsvService,
        @Autowired private val academyHistoryService: IAcademyHistoryService
) {
    @GetMapping()
    @LogExecution
    fun getAllStudiedDegrees(@RequestParam idStudent: Optional<Long>, @RequestParam idDegree: Optional<Long>, pageable: Pageable): Page<StudiedDegreeDTO> {
        return when {
            idDegree.isPresent && idStudent.isPresent -> academyHistoryService.getAllStudiedDegreesByStudentAndDegree(idStudent.get(), idDegree.get(), pageable)
            idDegree.isPresent -> academyHistoryService.getAllStudiedDegreesByDegree(idDegree.get(), pageable)
            idStudent.isPresent -> academyHistoryService.getAllStudiedDegreesByStudent(idStudent.get(), pageable)
            else -> academyHistoryService.getAllStudiedDegrees(pageable)
        }
    }
}
