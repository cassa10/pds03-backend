package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

abstract class CannotDeleteException(message: String) : RuntimeException(message)

private const val SUBJECT_CANNOT_DELETE = "subject cannot delete because has courses"

@ResponseStatus(code = HttpStatus.CONFLICT, reason = SUBJECT_CANNOT_DELETE)
class CannotDeleteSubjectWithCoursesException() : CannotDeleteException(SUBJECT_CANNOT_DELETE)