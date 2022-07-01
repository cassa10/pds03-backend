package ar.edu.unq.pds03backend.api.v2

import ar.edu.unq.pds03backend.dto.semester.SemesterResponseDTO
import ar.edu.unq.pds03backend.service.ISemesterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController(value = "V2SemesterController")
@RequestMapping("/api/v2/semester")
@Validated
class SemesterController(@Autowired val semesterService: ISemesterService) {

    @GetMapping("/all")
    fun getAllSemesters(pageable: Pageable): Page<SemesterResponseDTO> {
        return semesterService.getAll(pageable)
    }
}