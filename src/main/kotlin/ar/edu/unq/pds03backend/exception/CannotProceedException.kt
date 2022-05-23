package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

abstract class CannotProceedException(message: String) : RuntimeException(message)

private const val SUBJECT_CANNOT_DELETE = "subject cannot delete because has courses"
private const val COURSE_CANNOT_DELETE = "course cannot delete because has already quote request"
private const val STUDENT_HAS_ALREADY_ENROLLED_SUBJECT = "student has already enrolled with some subject"
private const val QUOTE_REQUEST_CANNOT_DELETE = "quote request cannot was delete because this state is approved or revoked"

@ResponseStatus(code = HttpStatus.CONFLICT, reason = SUBJECT_CANNOT_DELETE)
class CannotDeleteSubjectWithCoursesException: CannotProceedException(SUBJECT_CANNOT_DELETE)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = COURSE_CANNOT_DELETE)
class CannotDeleteCourseException: CannotProceedException(COURSE_CANNOT_DELETE)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = STUDENT_HAS_ALREADY_ENROLLED_SUBJECT)
class StudentHasAlreadyEnrolledSubject: CannotProceedException(STUDENT_HAS_ALREADY_ENROLLED_SUBJECT)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = STUDENT_HAS_ALREADY_ENROLLED_SUBJECT)
class CannotDeleteQuoteRequestException: CannotProceedException(STUDENT_HAS_ALREADY_ENROLLED_SUBJECT)
