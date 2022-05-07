package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

private const val DEGREE_ALREADY_EXISTS = "Degree already exists"

@ResponseStatus(code = HttpStatus.CONFLICT, reason = DEGREE_ALREADY_EXISTS)
class DegreeAlreadyExists : RuntimeException(DEGREE_ALREADY_EXISTS)