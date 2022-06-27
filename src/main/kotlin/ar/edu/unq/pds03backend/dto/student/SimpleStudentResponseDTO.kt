package ar.edu.unq.pds03backend.dto.student

data class SimpleStudentResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String
)