package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

private const val USER_NOT_FOUND = "user not found"
private const val STUDENT_NOT_FOUND = "student not found"
private const val DEGREE_NOT_FOUND = "degree not found"
private const val COURSE_NOT_FOUND = "course not found"
private const val QUOTE_REQUEST_NOT_FOUND = "quote request not found"
private const val SUBJECT_NOT_FOUND = "subject request not found"
private const val ANY_DEGREE_NOT_FOUND = "any degree not found"


abstract class NotFoundException(message: String) : RuntimeException(message)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = USER_NOT_FOUND)
class UserNotFoundException : NotFoundException(USER_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = STUDENT_NOT_FOUND)
class StudentNotFoundException : NotFoundException(STUDENT_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = DEGREE_NOT_FOUND)
class DegreeNotFoundException : NotFoundException(DEGREE_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ANY_DEGREE_NOT_FOUND)
class NotFoundAnyDegreeException : NotFoundException(ANY_DEGREE_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = COURSE_NOT_FOUND)
class CourseNotFoundException : NotFoundException(COURSE_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = QUOTE_REQUEST_NOT_FOUND)
class QuoteRequestNotFoundException : NotFoundException(QUOTE_REQUEST_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = SUBJECT_NOT_FOUND)
class SubjectNotFoundException : NotFoundException(SUBJECT_NOT_FOUND)