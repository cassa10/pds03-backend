package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.course.CourseResponseDTO
import ar.edu.unq.pds03backend.model.Course

object CourseMapper : Mapper<Course, CourseResponseDTO> {
    override fun toDTO(course: Course): CourseResponseDTO =
        CourseResponseDTO(
            id = course.id!!,
            semester = SemesterMapper.toDTO(course.semester),
            subject = SubjectMapper.toSimpleDTO(course.subject),
            name = course.name,
            assigned_teachers = course.assigned_teachers,
            //TODO: current_quotes
            current_quotes = 1,
            total_quotes = course.total_quotes,
        )
}