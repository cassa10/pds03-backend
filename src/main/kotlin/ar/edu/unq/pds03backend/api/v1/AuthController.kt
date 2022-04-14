package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.dto.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.LoginResponseDTO
import ar.edu.unq.pds03backend.service.impl.AuthService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.xml.ws.http.HTTPException

@RestController
@RequestMapping("/api/v1/login")
class AuthController(@Autowired val authService: AuthService) {

    @PostMapping
    @LogExecution
    fun login(@RequestBody loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        return try {
            authService.login(loginRequestDTO)
        } catch (e: Exception) {
            // TODO: Handle custom exception
            throw HTTPException(500)
        }
    }
}
