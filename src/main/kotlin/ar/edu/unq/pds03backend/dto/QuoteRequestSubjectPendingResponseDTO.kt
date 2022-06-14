package ar.edu.unq.pds03backend.dto

data class QuoteRequestSubjectPendingResponseDTO(
        val idSubject: Long,
        val name: String,
        val course: CourseResponseDTO,
        val total_quotes: Int,
        val availableQuotes: Int,
        val requestQuotes: Int
) {
    data class CourseResponseDTO(
            val idCourse: Long,
            val name: String
    )
}
