package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

private const val STUDENT_NOT_FOUND = "Student not found"

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = STUDENT_NOT_FOUND)
class StudentNotFound : RuntimeException(STUDENT_NOT_FOUND)