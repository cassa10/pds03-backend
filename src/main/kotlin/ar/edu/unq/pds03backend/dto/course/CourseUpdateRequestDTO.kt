package ar.edu.unq.pds03backend.dto.course

import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty


data class CourseUpdateRequestDTO(
    @field:NotBlank(message = "'name' must not be blank")
    val name: String,
    @field:NotEmpty(message = "'assignedTeachers' must not be empty")
    val assignedTeachers: List<String>,
    @field:Min(value = 1, message = "'totalQuotes' min value is 1")
    @field:Max(value = 300, message = "'totalQuotes' max value is 300")
    val totalQuotes: Int,
    @field:NotEmpty(message = "'hours' must not be empty")
    @field:Valid
    val hours: List<HourRequestDTO>
)