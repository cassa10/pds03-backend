package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.semester.SemesterRequestDTO
import ar.edu.unq.pds03backend.dto.semester.SemesterResponseDTO
import ar.edu.unq.pds03backend.dto.semester.UpdateSemesterRequestDTO
import ar.edu.unq.pds03backend.model.Semester
import ar.edu.unq.pds03backend.repository.ISemesterService
import ar.edu.unq.pds03backend.utils.SemesterHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/semester")
@Validated
class SemesterController(@Autowired val semesterService: ISemesterService) {

    @GetMapping
    fun getSemester(@RequestParam year: Optional<Int>, @RequestParam isSndSemester: Optional<Boolean>): SemesterResponseDTO {
        return when {
            year.isPresent && isSndSemester.isPresent -> semesterService.getSemesterByYearAndIsSndSemester(
                year.get(),
                isSndSemester.get()
            )
            year.isPresent -> semesterService.getSemesterByYearAndIsSndSemester(
                year.get(),
                SemesterHelper.currentIsSecondSemester
            )
            isSndSemester.isPresent -> semesterService.getSemesterByYearAndIsSndSemester(
                SemesterHelper.currentYear,
                isSndSemester.get()
            )
            else -> semesterService.getSemesterByYearAndIsSndSemester(
                SemesterHelper.currentYear,
                SemesterHelper.currentIsSecondSemester
            )
        }
    }

    @PostMapping
    fun createSemester(@Valid @RequestBody semesterRequestDTO: SemesterRequestDTO): SemesterResponseDTO {
        return semesterService.createSemester(semesterRequestDTO)
    }

    @GetMapping("/{id}")
    fun getByIdSemester(@PathVariable("id") idSemester: Long): SemesterResponseDTO {
        return semesterService.getSemesterById(idSemester)
    }

    @PutMapping("/{id}")
    fun updateSemester(
        @PathVariable("id") idSemester: Long,
        @Valid @RequestBody semesterRequestDTO: UpdateSemesterRequestDTO
    ): String {
        semesterService.updateSemester(idSemester, semesterRequestDTO)
        return "semester $idSemester updated"
    }

    @GetMapping("/all")
    fun getAllSemesters(): List<SemesterResponseDTO> {
        return semesterService.getAll()
    }

}