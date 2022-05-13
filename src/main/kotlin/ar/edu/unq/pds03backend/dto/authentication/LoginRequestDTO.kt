package ar.edu.unq.pds03backend.dto.authentication

import javax.validation.constraints.NotBlank

data class LoginRequestDTO(
    @field:NotBlank val email: String,
    @field:NotBlank val dni: String
)