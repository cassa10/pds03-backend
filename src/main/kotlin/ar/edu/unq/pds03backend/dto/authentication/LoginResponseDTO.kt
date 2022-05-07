package ar.edu.unq.pds03backend.dto.authentication

import ar.edu.unq.pds03backend.model.Role

data class LoginResponseDTO(val id: Long, val username: String, val email: String, val dni: String, val role: Role)