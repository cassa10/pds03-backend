package ar.edu.unq.pds03backend.dto.subject

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class SubjectRequestDTO(
    @field:NotEmpty val degreeIds: Collection<Long>,
    @field:NotBlank val name: String,
)