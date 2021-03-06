package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.GenericResponse
import ar.edu.unq.pds03backend.dto.course.CourseRequestDTO
import ar.edu.unq.pds03backend.dto.course.CourseResponseDTO
import ar.edu.unq.pds03backend.dto.course.CourseUpdateRequestDTO
import ar.edu.unq.pds03backend.service.ICourseService
import ar.edu.unq.pds03backend.service.ICsvService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/course")
@Validated
class CourseController(
    @Autowired private val courseService: ICourseService,
    @Autowired private val csvService: ICsvService
) {

    @PostMapping("/semester/{id_semester}/subject/{id_subject}")
    @LogExecution
    fun createCourse(
        @PathVariable("id_semester") idSemester: Long,
        @PathVariable("id_subject") idSubject: Long,
        @Valid @RequestBody courseRequestDTO: CourseRequestDTO
    ): GenericResponse {
        courseService.create(idSemester, idSubject, courseRequestDTO)
        return GenericResponse(HttpStatus.OK,"course created")
    }

    @PutMapping("/{id_course}")
    @LogExecution
    fun updateCourse(
        @PathVariable("id_course") idCourse: Long,
        @Valid @RequestBody courseUpdateRequestDTO: CourseUpdateRequestDTO
    ): GenericResponse {
        courseService.update(idCourse, courseUpdateRequestDTO)
        return GenericResponse(HttpStatus.OK,"course with id $idCourse updated")
    }

    @GetMapping("/semester/{id_semester}/subject/{id_subject}")
    @LogExecution
    fun getAllCourses(
        @PathVariable("id_semester") idSemester: Long,
        @PathVariable("id_subject") idSubject: Long
    ): List<CourseResponseDTO> {
        return courseService.getAllBySemesterAndSubject(idSemester, idSubject)
    }

    @GetMapping("/{id_course}")
    @LogExecution
    fun getCourse(@PathVariable("id_course") idCourse: Long): CourseResponseDTO {
        return courseService.getById(idCourse)
    }

    @DeleteMapping("/{id_course}")
    @LogExecution
    fun deleteCourse(@PathVariable("id_course") idCourse: Long): GenericResponse {
        courseService.delete(idCourse)
        return GenericResponse(HttpStatus.OK,"course deleted")
    }

    @PostMapping("/currents/import")
    @LogExecution
    fun importAcademyOfferCsv(@RequestParam("file") file: MultipartFile): GenericResponse {
        val data = csvService.parseAcademyOfferFile(file)
        courseService.importAcademyOffer(data)
        return GenericResponse(HttpStatus.OK, "imported successfully")
    }
}
