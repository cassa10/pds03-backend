package ar.edu.unq.pds03backend.dto.authentication

import javax.validation.constraints.NotBlank

class LoginRequestDTO(
    @field:NotBlank(message = "'dni' must not be blank") val dni: String,
    @field:NotBlank(message = "'email' must not be blank") val password: String,
){
    override fun toString(): String = "{dni=$dni, password=***}"
}