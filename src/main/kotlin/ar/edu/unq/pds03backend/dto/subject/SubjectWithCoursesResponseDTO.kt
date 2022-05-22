package ar.edu.unq.pds03backend.dto.subject

import ar.edu.unq.pds03backend.dto.course.SimpleCourseForSubjectDTO

data class SubjectWithCoursesResponseDTO(
    val subject: SimpleSubjectResponseDTO,
    val courses: List<SimpleCourseForSubjectDTO>,
)
{
    override fun equals(other: Any?): Boolean {
        other as SubjectWithCoursesResponseDTO
        return subject.id == other.subject.id
    }
}
