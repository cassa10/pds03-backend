package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

private const val DEGREE_NOT_FOUND = "Degree not found"

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = DEGREE_NOT_FOUND)
class DegreeNotFound : RuntimeException(DEGREE_NOT_FOUND)