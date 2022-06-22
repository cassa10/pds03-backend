package ar.edu.unq.pds03backend.api.v2

import ar.edu.unq.pds03backend.dto.authentication.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.service.IAuthService
import ar.edu.unq.pds03backend.service.IUserService
import ar.edu.unq.pds03backend.service.logger.LogExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@RestController(value = "V2AuthController")
@RequestMapping("/api/v2")
@Validated
class AuthController(
    @Autowired private val authService: IAuthService,
) {

    @PostMapping("/login")
    @LogExecution
    fun login(@Valid @RequestBody loginRequestDTO: LoginRequestDTO, response: HttpServletResponse): ResponseEntity<String> {
        val jwt = authService.login(loginRequestDTO).jwt
        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        response.addCookie(cookie)
        return ResponseEntity.ok(jwt)
    }


    @PostMapping("/logout")
    @LogExecution
    fun logout(response: HttpServletResponse): ResponseEntity<String> {
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0
        response.addCookie(cookie)
        return ResponseEntity.ok("logout success")
    }

    // TODO: Get by cookie (@CookieValue("jwt") jwt: String) ???
    // TODO: @RequestHeader (name="Authorization") handle in middleware in SecurityConfig
    @GetMapping("/user")
    @LogExecution
    fun getByJwt(@RequestHeader (name="Authorization") token: String): UserResponseDTO {
        return authService.getUserByToken(token)
    }



}