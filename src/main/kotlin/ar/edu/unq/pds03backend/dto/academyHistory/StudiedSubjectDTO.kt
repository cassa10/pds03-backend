package ar.edu.unq.pds03backend.dto.academyHistory

import ar.edu.unq.pds03backend.dto.subject.SimpleSubjectResponseDTO

data class StudiedSubjectDTO(
    val id: Long,
    val subject: SimpleSubjectResponseDTO,
    val mark: Int?,
    val status: String,
    val passed: Boolean,
    val inProgress: Boolean
)