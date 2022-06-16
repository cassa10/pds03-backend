package ar.edu.unq.pds03backend.dto.authentication

import javax.validation.constraints.NotBlank

data class LoginRequestDTO(
    @field:NotBlank(message = "'email' must not be blank") val email: String,
    @field:NotBlank(message = "'dni' must not be blank") val dni: String
)