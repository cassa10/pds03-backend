package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

private const val COURSE_NOT_FOUND = "Course not found"

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = COURSE_NOT_FOUND)
class CourseNotFound : RuntimeException(COURSE_NOT_FOUND)