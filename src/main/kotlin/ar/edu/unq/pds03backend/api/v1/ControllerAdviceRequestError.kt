package ar.edu.unq.pds03backend.api.v1

import ar.edu.unq.pds03backend.exception.UserNotFound
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class ControllerAdviceRequestError : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(UserNotFound::class)])
    fun handlerException(exception: Exception, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), exception.message!!)
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}

data class ErrorsDetails(val time: Date, val message: String)
