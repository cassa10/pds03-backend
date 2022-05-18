package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

abstract class AlreadyExistsException(message: String) : RuntimeException(message)

private const val DEGREE_ALREADY_EXISTS = "degree already exists"
private const val QUOTE_REQUEST_ALREADY_EXISTS = "quote request already exists"
private const val SUBJECT_NAME_ALREADY_EXISTS = "subject name request already exists"


@ResponseStatus(code = HttpStatus.CONFLICT, reason = DEGREE_ALREADY_EXISTS)
class DegreeAlreadyExistsException : AlreadyExistsException(DEGREE_ALREADY_EXISTS)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = QUOTE_REQUEST_ALREADY_EXISTS)
class QuoteRequestAlreadyExistsException : AlreadyExistsException(QUOTE_REQUEST_ALREADY_EXISTS)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = SUBJECT_NAME_ALREADY_EXISTS)
class SubjectNameAlreadyExistsException : AlreadyExistsException(SUBJECT_NAME_ALREADY_EXISTS)