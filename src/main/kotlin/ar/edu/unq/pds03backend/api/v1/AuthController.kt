package ar.edu.unq.pds03backend.api.v1

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController(value = "V1AuthController")
@RequestMapping("/api/v1/login")
class AuthController {
    @PostMapping
    @ResponseStatus(code = HttpStatus.MOVED_PERMANENTLY, reason = "endpoint deprecated, please use: '/api/v2/auth/login'")
    fun deprecatedLogin() {}
}
