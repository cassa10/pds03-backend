package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

private const val QUOTE_REQUEST_ALREADY_EXISTS = "Quote request already exists"

@ResponseStatus(code = HttpStatus.CONFLICT, reason = QUOTE_REQUEST_ALREADY_EXISTS)
class QuoteRequestAlreadyExists : RuntimeException(QUOTE_REQUEST_ALREADY_EXISTS)