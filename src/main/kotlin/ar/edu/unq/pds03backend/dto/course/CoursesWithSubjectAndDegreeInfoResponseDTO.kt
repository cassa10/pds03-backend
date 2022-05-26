package ar.edu.unq.pds03backend.dto.course

import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.mapper.SubjectMapper
import ar.edu.unq.pds03backend.model.Course

class CourseWithSubjectInfoResponseDTO(
    val id: Long,
    val name: String,
    val subject: SubjectResponseDTO,
) {
    class Mapper(private val course: Course) {
        fun map():CourseWithSubjectInfoResponseDTO =
            CourseWithSubjectInfoResponseDTO(
                id = course.id!!,
                name = course.name,
                subject = SubjectMapper.toDTO(course.subject)
            )
    }
}