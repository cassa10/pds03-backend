package ar.edu.unq.pds03backend.dto

data class CourseRequestDTO(
    val name: String,
    val assignedTeachers: List<String>,
    val totalQuotes: Int
)