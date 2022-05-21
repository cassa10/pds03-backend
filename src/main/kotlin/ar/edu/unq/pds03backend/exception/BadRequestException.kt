package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

abstract class BadRequestException(message: String) : RuntimeException(message)

private const val INVALID_REQUEST_HOURS = "some course hour is invalid"
private const val INVALID_REQUEST_COURSE_TOTAL_QUOTES = "course total_quotes is less than current total_quotes value"

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = INVALID_REQUEST_HOURS)
class InvalidRequestHours: BadRequestException(INVALID_REQUEST_HOURS)

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = INVALID_REQUEST_COURSE_TOTAL_QUOTES)
class CannotUpdateCourseInvalidTotalQuotesException: BadRequestException(INVALID_REQUEST_COURSE_TOTAL_QUOTES)