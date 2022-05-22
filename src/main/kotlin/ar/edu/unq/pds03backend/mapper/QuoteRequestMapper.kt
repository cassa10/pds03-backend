package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.course.SimpleCourseResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.semester.SemesterResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SimpleSubjectResponseDTO
import ar.edu.unq.pds03backend.model.QuoteRequest

object QuoteRequestMapper : Mapper<QuoteRequest, QuoteRequestResponseDTO> {
    override fun toDTO(quoteRequest: QuoteRequest): QuoteRequestResponseDTO {
        return QuoteRequestResponseDTO(
            id = quoteRequest.id!!,
            course = SimpleCourseResponseDTO(
                id = quoteRequest.course.id!!,
                semester = SemesterResponseDTO(
                    quoteRequest.course.semester.id!!,
                    quoteRequest.course.semester.isSndSemester,
                    quoteRequest.course.semester.year,
                    quoteRequest.course.semester.name
                ),
                subject = SimpleSubjectResponseDTO(quoteRequest.course.subject.id!!, quoteRequest.course.subject.name),
                name = quoteRequest.course.name,
                assigned_teachers = quoteRequest.course.assigned_teachers,
                totalQuotes = quoteRequest.course.total_quotes
            ),
            student = StudentResponseDTO(
                id = quoteRequest.student.id!!,
                firstName = quoteRequest.student.firstName,
                lastName = quoteRequest.student.lastName,
                dni = quoteRequest.student.dni,
                email = quoteRequest.student.email,
                legajo = quoteRequest.student.legajo,
                username = quoteRequest.student.username,
            ),
            state = quoteRequest.state,
            comment = quoteRequest.comment
        )
    }
}