package ar.edu.unq.pds03backend.dto.degree

import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO

data class DegreeResponseDTO(
    val id: Long,
    val name: String,
    val acronym: String,
    val subjects: Collection<SubjectResponseDTO>
)