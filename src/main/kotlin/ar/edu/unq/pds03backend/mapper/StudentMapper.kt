package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.student.StudentResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesAndSubjectsResponseDTO
import ar.edu.unq.pds03backend.dto.degree.EnrolledDegreeResponseDTO
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.Student
import ar.edu.unq.pds03backend.model.Warning

object StudentMapper : Mapper<Student, StudentResponseDTO> {
    override fun toDTO(student: Student): StudentResponseDTO = StudentResponseDTO(
        id = student.id!!,
        firstName = student.firstName,
        lastName = student.lastName,
        dni = student.dni,
        email = student.email,
        legajo = student.legajo,
        maxCoefficient = student.maxCoefficient(),
        enrolledDegrees = student.enrolledDegrees.map { EnrolledDegreeResponseDTO.Mapper(it, student.getStudiedDegreeCoefficient(it)).map() },
    )

    fun toStudentWithQuotesAndSubjectsResponseDTO(
        student: Student,
        quoteRequestsWithWarnings: List<Pair<QuoteRequest, List<Warning>>>
    ): StudentWithQuotesAndSubjectsResponseDTO =
        StudentWithQuotesAndSubjectsResponseDTO(
            id = student.id!!,
            firstName = student.firstName,
            lastName = student.lastName,
            dni = student.dni,
            email = student.email,
            legajo = student.legajo,
            enrolledSubjects = student.enrolledCourses.groupBy { it.subject }
                .map { SubjectMapper.toSubjectWithCoursesDTO(it.key, it.value) },
            quoteRequests = quoteRequestsWithWarnings.map { QuoteRequestMapper.toQuoteRequestWithoutStudentResponseDTO(it.first, it.second) },
            maxCoefficient = student.maxCoefficient(),
            enrolledDegrees = student.enrolledDegrees.map { EnrolledDegreeResponseDTO.Mapper(it, student.getStudiedDegreeCoefficient(it)).map() },
        )

}