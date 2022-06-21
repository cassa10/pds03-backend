package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

const val INVALID_PASSWORD = "invalid credentials"
const val INVALID_TOKEN = "token is invalid"
const val EXPIRED_TOKEN = "token has expired"

abstract class UnauthorizedException(message: String) : RuntimeException(message)

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = INVALID_PASSWORD)
class InvalidPassword: UnauthorizedException(INVALID_PASSWORD)

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = EXPIRED_TOKEN)
class ExpiredTokenException: UnauthorizedException(EXPIRED_TOKEN)

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = INVALID_TOKEN)
class InvalidTokenException: UnauthorizedException(INVALID_TOKEN)