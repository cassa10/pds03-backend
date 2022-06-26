package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.GenericResponse
import ar.edu.unq.pds03backend.dto.user.StudentRegisterRequestDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.service.IAuthService
import ar.edu.unq.pds03backend.service.IUserService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(@Autowired private val userService: IUserService) {

    @GetMapping("/{id}")
    @LogExecution
    fun getById(@PathVariable @Valid id: Long): UserResponseDTO = userService.getById(id)

    @PostMapping("/register/student")
    fun create(@RequestBody @Valid studentRegisterReq: StudentRegisterRequestDTO): GenericResponse {
        userService.createStudent(studentRegisterReq.mapToUser())
        return GenericResponse(HttpStatus.OK, "user created successfully")
    }

}