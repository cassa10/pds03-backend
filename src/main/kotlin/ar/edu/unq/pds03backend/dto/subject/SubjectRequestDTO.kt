package ar.edu.unq.pds03backend.dto.subject

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class SubjectRequestDTO(
    @field:NotEmpty(message = "'degreeIds' must not be empty") val degreeIds: Collection<Long>,
    @field:NotBlank(message = "'name' must not be blank") val name: String,
    @field:NotNull(message = "'prerequisiteSubjects' must not be null") val prerequisiteSubjects: Collection<Long>,
)