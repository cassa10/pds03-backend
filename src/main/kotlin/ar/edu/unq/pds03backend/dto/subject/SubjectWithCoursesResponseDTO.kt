package ar.edu.unq.pds03backend.dto.subject


import ar.edu.unq.pds03backend.dto.course.SimpleCourseResponseDTO

data class SubjectWithCoursesResponseDTO(
    val subject: SimpleSubjectResponseDTO,
    val courses: List<SimpleCourseResponseDTO>,
)
{
    override fun equals(other: Any?): Boolean {
        other as SubjectWithCoursesResponseDTO
        return subject.id == other.subject.id
    }
}
