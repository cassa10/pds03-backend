package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.student.StudentResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesAndSubjectsResponseDTO
import ar.edu.unq.pds03backend.dto.degree.EnrolledDegreeResponseDTO
import ar.edu.unq.pds03backend.dto.student.SimpleStudentResponseDTO
import ar.edu.unq.pds03backend.model.QuoteRequestWithAdditionalInfo
import ar.edu.unq.pds03backend.model.Student

object StudentMapper : Mapper<Student, StudentResponseDTO> {
    override fun toDTO(student: Student): StudentResponseDTO = StudentResponseDTO(
        id = student.id!!,
        firstName = student.firstName,
        lastName = student.lastName,
        dni = student.dni,
        email = student.email,
        legajo = student.legajo,
        maxCoefficient = student.maxCoefficient(),
        enrolledDegrees = student.enrolledDegrees.map {
            EnrolledDegreeResponseDTO.Mapper(
                it,
                student.getStudiedDegreeCoefficient(it),
                student.getStudiedDegreeByDegree(it)
            ).map()
        },
    )

    fun toStudentWithQuotesAndSubjectsResponseDTO(
        student: Student,
        quoteRequestsWithAdditionalInfo: List<QuoteRequestWithAdditionalInfo>
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
            quoteRequests = quoteRequestsWithAdditionalInfo.map {
                QuoteRequestMapper.toQuoteRequestWithoutStudentResponseDTO(
                    quoteRequest = it.quoteRequest,
                    warnings = it.warnings,
                    availableQuotes = it.availableQuotes,
                    requestedQuotes = it.requestedQuotes
                )
            },
            maxCoefficient = student.maxCoefficient(),
            enrolledDegrees = student.enrolledDegrees.map {
                EnrolledDegreeResponseDTO.Mapper(
                    it,
                    student.getStudiedDegreeCoefficient(it),
                    student.getStudiedDegreeByDegree(it)
                ).map()
            },
        )

    fun toSimpleStudentResponseDTO(student: Student): SimpleStudentResponseDTO = SimpleStudentResponseDTO(
        student.id!!,
        student.firstName,
        student.lastName,
        student.dni,
        student.email,
        student.legajo
    )
}