package ar.edu.unq.pds03backend.dto.user

import ar.edu.unq.pds03backend.dto.course.CourseResponseDTO

data class UserResponseDTO(
    val id: Long,
    val username: String
)