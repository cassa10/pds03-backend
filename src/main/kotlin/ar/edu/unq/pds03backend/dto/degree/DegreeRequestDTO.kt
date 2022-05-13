package ar.edu.unq.pds03backend.dto.degree

import javax.validation.constraints.NotBlank

data class DegreeRequestDTO(
    @field:NotBlank val name: String,
    @field:NotBlank val acronym: String
)