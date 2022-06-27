package ar.edu.unq.pds03backend.dto.academyHistory

import ar.edu.unq.pds03backend.dto.degree.SimpleDegreeResponseDTO
import ar.edu.unq.pds03backend.dto.student.SimpleStudentResponseDTO

data class StudiedDegreeDTO(
    val id: Long,
    val coefficient: Float,
    val degree: SimpleDegreeResponseDTO,
    val student: SimpleStudentResponseDTO,
    val studiedSubjects: List<StudiedSubjectDTO>
)