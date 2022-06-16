package ar.edu.unq.pds03backend.dto.quoteRequest

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class QuoteRequestRequestDTO(
    @field:NotNull(message = "'idStudent' must not be null") val idStudent: Long,
    @field:NotEmpty(message = "'idCourses' must not be empty") val idCourses: List<Long>,
    @field:NotNull(message = "'comment' must not be null") val comment: String
)