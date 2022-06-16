package ar.edu.unq.pds03backend.dto.degree

import javax.validation.constraints.NotBlank

data class DegreeRequestDTO(
    @field:NotBlank(message = "'name' must not be blank") val name: String,
    @field:NotBlank(message = "'acronym' must not be blank") val acronym: String
)