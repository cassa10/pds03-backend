package ar.edu.unq.pds03backend.dto.quoteRequest

import ar.edu.unq.pds03backend.dto.course.SimpleCourseResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentResponseDTO
import ar.edu.unq.pds03backend.model.QuoteState

data class QuoteRequestResponseDTO(
    val id: Long,
    val course: SimpleCourseResponseDTO,
    val student: StudentResponseDTO,
    val state: QuoteState,
    val comment: String
)