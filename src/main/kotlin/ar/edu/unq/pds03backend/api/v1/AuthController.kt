package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.authentication.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.authentication.LoginResponseDTO
import ar.edu.unq.pds03backend.service.impl.AuthService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/login")
class AuthController(@Autowired private val authService: AuthService) {

    @PostMapping
    @LogExecution
    fun login(@RequestBody loginRequestDTO: LoginRequestDTO): LoginResponseDTO = authService.login(loginRequestDTO)
}
