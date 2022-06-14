package ar.edu.unq.pds03backend.dto.subject

import ar.edu.unq.pds03backend.dto.degree.SimpleDegreeResponseDTO

data class SubjectResponseDTO(
    val id: Long,
    val name: String,
    val degrees: List<SimpleDegreeResponseDTO>,
    val prerequisiteSubjects: List<SimpleSubjectResponseDTO>,
)
