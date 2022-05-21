package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.course.CourseResponseDTO
import ar.edu.unq.pds03backend.dto.course.HourResponseDTO
import ar.edu.unq.pds03backend.model.Course

object CourseMapper {
    fun toDTO(course: Course, requestedQuotes: Int, acceptedQuotes: Int): CourseResponseDTO = CourseResponseDTO(
        id = course.id!!,
        semester = SemesterMapper.toDTO(course.semester),
        subject = SubjectMapper.toSimpleDTO(course.subject),
        name = course.name,
        assigned_teachers = course.assigned_teachers,
        hours = course.hours.map { HourResponseDTO(day = it.day, from = it.getFromString(), to = it.getToString()) },
        totalQuotes = course.total_quotes,
        currentQuotes = course.total_quotes - acceptedQuotes,
        acceptedQuotes = acceptedQuotes,
        requestedQuotes = requestedQuotes,
    )
}