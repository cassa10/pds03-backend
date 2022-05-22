package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.service.impl.UserService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(@Autowired val userService: UserService) {

    @GetMapping("/{id}")
    @LogExecution
    fun getById(@PathVariable @Valid id: Long): UserResponseDTO = userService.getById(id)
}