package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.student.StudentResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesAndSubjectsResponseDTO
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.Student

object StudentMapper : Mapper<Student, StudentResponseDTO> {
    override fun toDTO(student: Student): StudentResponseDTO = StudentResponseDTO(
        id = student.id!!,
        firstName = student.firstName,
        lastName = student.lastName,
        dni = student.dni,
        email = student.email,
        legajo = student.legajo,
        username = student.username,
    )

    fun toStudentWithQuotesAndSubjectsResponseDTO(student: Student, quoteRequests: List<QuoteRequest>): StudentWithQuotesAndSubjectsResponseDTO =
        StudentWithQuotesAndSubjectsResponseDTO(
            student.id!!,
            student.firstName,
            student.lastName,
            student.dni,
            student.email,
            student.legajo,
            student.username,
            student.enrolledCourses.groupBy { it.subject }
                .map { SubjectMapper.toSubjectWithCoursesDTO(it.key, it.value) },
            quoteRequests.map { QuoteRequestMapper.toQuoteRequestWithoutStudentResponseDTO(it) }
        )

}