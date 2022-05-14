package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.authentication.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.authentication.LoginResponseDTO
import ar.edu.unq.pds03backend.service.IAuthService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/login")
@Validated
class AuthController(@Autowired private val authService: IAuthService) {

    @PostMapping
    @LogExecution
    fun login(@Valid @RequestBody loginRequestDTO: LoginRequestDTO): LoginResponseDTO = authService.login(loginRequestDTO)
}
