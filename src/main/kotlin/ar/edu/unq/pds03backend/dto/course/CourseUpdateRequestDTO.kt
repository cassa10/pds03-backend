package ar.edu.unq.pds03backend.dto.course

import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty


data class CourseUpdateRequestDTO(
    @field:NotBlank
    val name: String,
    @field:NotEmpty
    val assignedTeachers: List<String>,
    @field:Min(value = 1)
    val totalQuotes: Int,
    @field:NotEmpty
    @field:Valid
    val hours: List<HourRequestDTO>
)