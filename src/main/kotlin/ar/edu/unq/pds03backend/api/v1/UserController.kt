package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.GenericResponse
import ar.edu.unq.pds03backend.dto.user.DirectorRegisterRequestDTO
import ar.edu.unq.pds03backend.dto.user.StudentRegisterRequestDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.service.ICsvService
import ar.edu.unq.pds03backend.service.IUserService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    @Autowired private val userService: IUserService,
    @Autowired private val csvService: ICsvService,
) {

    @GetMapping("/{id}")
    @LogExecution
    fun getById(@PathVariable @Valid id: Long): UserResponseDTO = userService.getById(id)

    @PostMapping("/register/student")
    fun createStudent(@RequestBody @Valid studentRegisterReq: StudentRegisterRequestDTO): GenericResponse {
        userService.createUser(studentRegisterReq.mapToUser())
        return GenericResponse(HttpStatus.OK, "student created successfully")
    }

    @PostMapping("/register/director")
    fun createDirector(@RequestBody @Valid directorRegisterDTO: DirectorRegisterRequestDTO): GenericResponse {
        userService.createUser(directorRegisterDTO.mapToUser())
        return GenericResponse(HttpStatus.OK, "student created successfully")
    }

    @PutMapping("/student/{id}")
    fun update(@PathVariable @Valid id: Long, @RequestBody @Valid studentUpdateReq: StudentRegisterRequestDTO): GenericResponse {
        userService.update(id, studentUpdateReq)
        return GenericResponse(HttpStatus.OK, "user updated successfully")
    }

    @PostMapping("/register/student/massive")
    @LogExecution
    fun massiveCreation(@RequestParam("file") file: MultipartFile): GenericResponse {
        val students = csvService.parseStudentsWithDegree(file)
        val successfulEntries = userService.createOrUpdateStudents(students)
        return GenericResponse(HttpStatus.OK,"massive registration with ${students.size} entries was successfullly with $successfulEntries entries")
    }

    @PostMapping("/student/enrolled/courses/current")
    @LogExecution
    fun massiveCourseRegistration(@RequestParam("file") file: MultipartFile): GenericResponse {
        val courseRegistration = csvService.parseStudentsCoursesRegistration(file)
        val successfulEntries = userService.importMassiveCourseRegistration(courseRegistration)
        return GenericResponse(HttpStatus.OK,"massive course registration with ${courseRegistration.size} entries was successfully with $successfulEntries entries")
    }
}