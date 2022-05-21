package ar.edu.unq.pds03backend.dto.subject

import ar.edu.unq.pds03backend.dto.course.SimpleCourseForSubjectDTO

data class SubjectWithCoursesResponseDTO(
    val subject: SimpleSubjectResponseDTO,
    val courses: List<SimpleCourseForSubjectDTO>,
)
