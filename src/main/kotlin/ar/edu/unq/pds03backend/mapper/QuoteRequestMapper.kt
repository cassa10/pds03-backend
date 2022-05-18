package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.course.CourseResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.semester.SemesterResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SimpleSubjectResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.model.QuoteRequest

object QuoteRequestMapper : Mapper<QuoteRequest, QuoteRequestResponseDTO> {
    override fun toDTO(quoteRequest: QuoteRequest): QuoteRequestResponseDTO {
        return QuoteRequestResponseDTO(
                id = quoteRequest.id!!,
                course = CourseResponseDTO(
                        quoteRequest.course.id!!,
                        SemesterResponseDTO(
                                quoteRequest.course.semester.id!!,
                                quoteRequest.course.semester.semester,
                                quoteRequest.course.semester.year,
                                quoteRequest.course.semester.name
                        ),
                        SimpleSubjectResponseDTO(quoteRequest.course.subject.id!!, quoteRequest.course.subject.name),
                        quoteRequest.course.name,
                        quoteRequest.course.assigned_teachers,
                        1,
                        quoteRequest.course.total_quotes
                ),
                student = StudentResponseDTO(
                        quoteRequest.student.id!!,
                        quoteRequest.student.firstName,
                        quoteRequest.student.lastName,
                        quoteRequest.student.dni,
                        quoteRequest.student.email,
                        quoteRequest.student.legajo,
                        UserResponseDTO(quoteRequest.student.user.id!!, quoteRequest.student.user.username)
                ),
                state = quoteRequest.state,
                comment = quoteRequest.comment
        )
    }
}