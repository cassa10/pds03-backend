package ar.edu.unq.pds03backend.dto.quoteRequest

import ar.edu.unq.pds03backend.dto.course.SimpleCourseResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentResponseDTO
import ar.edu.unq.pds03backend.mapper.CourseMapper
import ar.edu.unq.pds03backend.mapper.StudentMapper
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.model.Warning
import java.time.LocalDateTime

class QuoteRequestWithWarningsResponseDTO(
    val id: Long,
    val course: SimpleCourseResponseDTO,
    val student: StudentResponseDTO,
    val state: QuoteState,
    val comment: String,
    val createdOn: LocalDateTime,
    val warnings: List<Warning>,
) {
    class Mapper(private val quoteRequest: QuoteRequest, private val warnings: List<Warning>) {
        fun map(): QuoteRequestWithWarningsResponseDTO = QuoteRequestWithWarningsResponseDTO(
            id = quoteRequest.id!!,
            course = CourseMapper.toSimpleCourseResponseDTO(quoteRequest.course),
            student = StudentMapper.toDTO(quoteRequest.student),
            state = quoteRequest.state,
            comment = quoteRequest.comment,
            createdOn = quoteRequest.createdOn,
            warnings = warnings,
        )
    }
}