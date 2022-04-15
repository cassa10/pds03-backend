package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

private const val USER_NOT_FOUND = "User not found"

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = USER_NOT_FOUND)
class UserNotFound : RuntimeException(USER_NOT_FOUND)