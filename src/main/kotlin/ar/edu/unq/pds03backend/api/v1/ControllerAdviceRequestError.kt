package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.exception.AlreadyExistsException
import ar.edu.unq.pds03backend.exception.BadRequestException
import ar.edu.unq.pds03backend.exception.CannotProceedException
import ar.edu.unq.pds03backend.exception.NotFoundException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import javax.validation.ConstraintViolationException


@ControllerAdvice
class ControllerAdviceRequestError : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(ConstraintViolationException::class), (BadRequestException::class)])
    fun handlerBadRequestException(exception: Exception, request: WebRequest): ResponseEntity<ErrorDetail> {
        val errorDetails = ErrorDetail(Date(), exception.message!!)
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(NotFoundException::class)])
    fun handlerNotFoundException(exception: Exception, request: WebRequest): ResponseEntity<ErrorDetail> {
        val errorDetails = ErrorDetail(Date(), exception.message!!)
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [(AlreadyExistsException::class), (CannotProceedException::class)])
    fun handlerAlreadyExistsException(exception: Exception, request: WebRequest): ResponseEntity<ErrorDetail> {
        val errorDetails = ErrorDetail(Date(), exception.message!!)
        return ResponseEntity(errorDetails, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(MissingKotlinParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST, reason="Invalid request, not found all required fields")
    fun handleMissingKotlinParameterException(e: MissingKotlinParameterException) {}

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.map { it.defaultMessage }
        val errorDetails = ErrorDetail(Date(), errors.toString())
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    override fun handleBindException(
        e: BindException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val subErrors = e.fieldErrors.map { it.defaultMessage }
        val errorDetails = ErrorDetail(Date(), "invalid ${e.objectName}, reason: $subErrors")
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorDetails = ErrorDetail(Date(), "page not found, ${ex.requestURL}")
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }
}

data class ErrorDetail(val time: Date, val message: String)
