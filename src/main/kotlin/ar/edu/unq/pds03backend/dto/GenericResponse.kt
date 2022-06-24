package ar.edu.unq.pds03backend.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus

class GenericResponse(
    @JsonIgnore
    val httpStatus: HttpStatus,
    val message: String,
    var status: String = "",
    var statusCode: Int = 0,
){
    init {
       status = httpStatus.name
       statusCode = httpStatus.value()
    }
}