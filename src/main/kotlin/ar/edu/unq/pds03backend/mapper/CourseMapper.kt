package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.course.CourseResponseDTO
import ar.edu.unq.pds03backend.dto.course.SimpleCourseForSubjectDTO
import ar.edu.unq.pds03backend.model.Course

object CourseMapper {
    fun toDTO(course: Course, requestedQuotes: Int, acceptedQuotes: Int): CourseResponseDTO =
        CourseResponseDTO(
            id = course.id!!,
            semester = SemesterMapper.toDTO(course.semester),
            subject = SubjectMapper.toSimpleDTO(course.subject),
            name = course.name,
            assigned_teachers = course.assigned_teachers,
            totalQuotes = course.total_quotes,
            currentQuotes = course.total_quotes - acceptedQuotes,
            acceptedQuotes = acceptedQuotes,
            requestedQuotes = requestedQuotes,
        )

    fun toSimpleForSubjectDTO(course: Course): SimpleCourseForSubjectDTO =
        SimpleCourseForSubjectDTO(id = course.id!!, name = course.name)
}