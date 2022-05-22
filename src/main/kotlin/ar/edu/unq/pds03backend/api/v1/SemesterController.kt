package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.model.Semester
import ar.edu.unq.pds03backend.repository.ISemesterService
import ar.edu.unq.pds03backend.utils.SemesterHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/semester")
class SemesterController(@Autowired val semesterService: ISemesterService) {

    @GetMapping
    fun getSemester(@RequestParam year: Optional<Int>, @RequestParam isSndSemester: Optional<Boolean>): Semester {
        return when {
            year.isPresent && isSndSemester.isPresent -> semesterService.getSemesterByYearAndIsSndSemester(year.get(), isSndSemester.get())
            year.isPresent -> semesterService.getSemesterByYearAndIsSndSemester(year.get(), SemesterHelper.currentSecondSemester)
            isSndSemester.isPresent -> semesterService.getSemesterByYearAndIsSndSemester(SemesterHelper.currentYear, isSndSemester.get())
            else -> semesterService.getSemesterByYearAndIsSndSemester(SemesterHelper.currentYear, SemesterHelper.currentSecondSemester)
        }
    }

}