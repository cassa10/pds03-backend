package ar.edu.unq.pds03backend.dto.course

data class CourseRequestDTO(
    val name: String,
    val assignedTeachers: List<String>,
    val totalQuotes: Int,
    val hours: List<HourRequestDTO>
)