package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

abstract class AlreadyExistsException(message: String) : RuntimeException(message)

private const val DEGREE_ALREADY_EXISTS = "degree already exists"
private const val QUOTE_REQUEST_ALREADY_EXISTS = "quote request already exists"
private const val SUBJECT_NAME_ALREADY_EXISTS = "subject name request already exists"
private const val SEMESTER_ALREADY_EXISTS = "semester already exists"
private const val USER_ALREADY_EXISTS = "user with dni or email already exists"
private const val COURSE_ALREADY_EXISTS = "course with externalId, subject and current semester already exists"



@ResponseStatus(code = HttpStatus.CONFLICT, reason = DEGREE_ALREADY_EXISTS)
class DegreeAlreadyExistsException : AlreadyExistsException(DEGREE_ALREADY_EXISTS)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = QUOTE_REQUEST_ALREADY_EXISTS)
class QuoteRequestAlreadyExistsException : AlreadyExistsException(QUOTE_REQUEST_ALREADY_EXISTS)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = SUBJECT_NAME_ALREADY_EXISTS)
class SubjectNameAlreadyExistsException : AlreadyExistsException(SUBJECT_NAME_ALREADY_EXISTS)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = SEMESTER_ALREADY_EXISTS)
class SemesterAlreadyExistException: AlreadyExistsException(SEMESTER_ALREADY_EXISTS)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = USER_ALREADY_EXISTS)
class UserAlreadyExistException: AlreadyExistsException(USER_ALREADY_EXISTS)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = COURSE_ALREADY_EXISTS)
class CourseAlreadyExist: AlreadyExistsException(COURSE_ALREADY_EXISTS)