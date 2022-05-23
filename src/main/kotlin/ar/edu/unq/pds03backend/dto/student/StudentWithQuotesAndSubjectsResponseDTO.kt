package ar.edu.unq.pds03backend.dto.student

data class StudentWithQuotesAndSubjectsResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String,
    val username: String,
    // materias inscripto
    // solicitudes de cupo
)