package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

abstract class NotFoundException(message: String) : RuntimeException(message)

private const val USER_NOT_FOUND = "User not found"
private const val STUDENT_NOT_FOUND = "Student not found"
private const val DEGREE_NOT_FOUND = "Degree not found"
private const val COURSE_NOT_FOUND = "Course not found"

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = USER_NOT_FOUND)
class UserNotFoundException : NotFoundException(USER_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = STUDENT_NOT_FOUND)
class StudentNotFoundException : NotFoundException(STUDENT_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = DEGREE_NOT_FOUND)
class DegreeNotFoundException : NotFoundException(DEGREE_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = COURSE_NOT_FOUND)
class CourseNotFoundException : NotFoundException(COURSE_NOT_FOUND)