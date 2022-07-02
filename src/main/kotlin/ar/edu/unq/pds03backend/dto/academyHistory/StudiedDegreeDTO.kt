package ar.edu.unq.pds03backend.dto.academyHistory

import ar.edu.unq.pds03backend.dto.degree.SimpleDegreeResponseDTO
import ar.edu.unq.pds03backend.dto.student.SimpleStudentResponseDTO
import ar.edu.unq.pds03backend.model.QualityState
import ar.edu.unq.pds03backend.model.RegistryState

data class StudiedDegreeDTO(
    val id: Long,
    val coefficient: Float,
    val degree: SimpleDegreeResponseDTO,
    val student: SimpleStudentResponseDTO,
    val registryState: RegistryState,
    val plan: String,
    val quality: QualityState,
    val location: String,
    val isRegular: Boolean,
    val studiedSubjects: List<StudiedSubjectDTO>
)