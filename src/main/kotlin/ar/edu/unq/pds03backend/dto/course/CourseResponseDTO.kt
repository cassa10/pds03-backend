package ar.edu.unq.pds03backend.dto.course

import ar.edu.unq.pds03backend.dto.semester.SemesterResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO

data class CourseResponseDTO(
    val id: Long,
    val semester: SemesterResponseDTO,
    val subject: SubjectResponseDTO,
    val number: Int,
    val name: String,
    val assigned_teachers: String,
    val current_quotes: Int,
    val total_quotes: Int,
)