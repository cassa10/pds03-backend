package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.GenericResponse
import ar.edu.unq.pds03backend.dto.academyHistory.StudiedDegreeDTO
import ar.edu.unq.pds03backend.service.IAcademyHistoryService
import ar.edu.unq.pds03backend.service.ICsvService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api/v1/academy_history")
@Validated
class AcademyHistoryController(
    @Autowired private val csvService: ICsvService,
    @Autowired private val academyHistoryService: IAcademyHistoryService
) {

    @PostMapping("/import")
    @LogExecution
    fun importAcademyHistoryCsv(@RequestParam("file") file: MultipartFile): GenericResponse {
        val data = csvService.parseAcademyHistoriesFile(file)
        academyHistoryService.updateAcademyHistory(data)
        return GenericResponse(HttpStatus.OK,"imported successfully")
    }

    @GetMapping()
    @LogExecution
    fun getAllStudiedDegrees(@RequestParam idStudent: Optional<Long>, @RequestParam idDegree: Optional<Long>): List<StudiedDegreeDTO> {
        return when {
            idDegree.isPresent && idStudent.isPresent -> academyHistoryService.getAllStudiedDegreesByStudentAndDegree(idStudent.get(), idDegree.get())
            idDegree.isPresent -> academyHistoryService.getAllStudiedDegreesByDegree(idDegree.get())
            idStudent.isPresent -> academyHistoryService.getAllStudiedDegreesByStudent(idStudent.get())
            else -> academyHistoryService.getAllStudiedDegrees()
        }
    }
}