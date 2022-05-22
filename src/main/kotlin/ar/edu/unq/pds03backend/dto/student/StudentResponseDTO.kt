package ar.edu.unq.pds03backend.dto.student

import ar.edu.unq.pds03backend.dto.user.SimpleUserResponseDTO

data class StudentResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String,
    val user: SimpleUserResponseDTO,
)