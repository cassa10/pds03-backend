package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.course.CourseRequestDTO
import ar.edu.unq.pds03backend.service.ICourseService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/semester")
class CourseController(@Autowired private val courseService: ICourseService) {

    @PostMapping("/{id_semester}/subject/{id_subject}")
    @LogExecution
    fun create(@PathVariable("id_semester") idSemester: Long,
               @PathVariable("id_subject") idSubject: Long,
               @RequestBody courseRequestDTO: CourseRequestDTO): String {
        courseService.create(idSemester, idSubject, courseRequestDTO)
        return "course created"
    }
}
