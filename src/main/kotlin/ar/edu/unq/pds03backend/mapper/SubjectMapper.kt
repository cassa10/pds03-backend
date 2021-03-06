package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.degree.SimpleDegreeResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SimpleSubjectResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectWithCoursesResponseDTO
import ar.edu.unq.pds03backend.model.Course
import ar.edu.unq.pds03backend.model.Subject

object SubjectMapper : Mapper<Subject, SubjectResponseDTO> {
    override fun toDTO(subject: Subject): SubjectResponseDTO =
        SubjectResponseDTO(
            id = subject.id!!,
            name = subject.name,
            degrees = subject.degrees.map {
                SimpleDegreeResponseDTO(
                    id = it.id!!,
                    name = it.name,
                    acronym = it.acronym,
                )
            },
            prerequisiteSubjects = subject.prerequisiteSubjects.map {
                toSimpleDTO(it)
            }
        )

    fun toSimpleDTO(subject: Subject): SimpleSubjectResponseDTO =
        SimpleSubjectResponseDTO(
            id = subject.id!!,
            name = subject.name,
        )

    fun toSubjectWithCoursesDTO(subject: Subject, courses: List<Course>): SubjectWithCoursesResponseDTO =
        SubjectWithCoursesResponseDTO(
            subject = toSimpleDTO(subject),
            courses = courses.map { course -> CourseMapper.toSimpleCourseResponseDTO(course) }
        )
}