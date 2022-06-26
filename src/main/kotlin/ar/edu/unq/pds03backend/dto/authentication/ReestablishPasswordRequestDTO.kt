package ar.edu.unq.pds03backend.dto.authentication

import javax.validation.constraints.NotBlank

class ReestablishPasswordRequestDTO(
    @field:NotBlank(message = "dni mustn't be empty") val dni: String,
)