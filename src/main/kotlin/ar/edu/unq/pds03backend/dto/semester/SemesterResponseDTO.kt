package ar.edu.unq.pds03backend.dto.semester

data class SemesterResponseDTO(
    val id: Long,
    val semester: Boolean,
    val year: Int,
    val name: String
)