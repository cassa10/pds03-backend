package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.exception.AlreadyExistsException
import ar.edu.unq.pds03backend.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ControllerAdviceRequestError : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(ConstraintViolationException::class)])
    fun handlerBadRequestException(exception: Exception, request: WebRequest): ResponseEntity<ErrorDetail> {
        val errorDetails = ErrorDetail(Date(), exception.message!!)
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(NotFoundException::class)])
    fun handlerNotFoundException(exception: Exception, request: WebRequest): ResponseEntity<ErrorDetail> {
        val errorDetails = ErrorDetail(Date(), exception.message!!)
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [(AlreadyExistsException::class)])
    fun handlerAlreadyExistsException(exception: Exception, request: WebRequest): ResponseEntity<ErrorDetail> {
        val errorDetails = ErrorDetail(Date(), exception.message!!)
        return ResponseEntity(errorDetails, HttpStatus.CONFLICT)
    }
}

data class ErrorDetail(val time: Date, val message: String)
