package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.service.ICsvService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/academy_history")
@Validated
class AcademyHistoryController(@Autowired private val csvService: ICsvService) {

    @PostMapping
    @LogExecution
    fun uploadAcademyHistoryCsv(@RequestParam("file") file: MultipartFile): String {
        csvService.uploadCsvFile(file)
        return "imported successfully"
    }

}