package ar.edu.unq.pds03backend.dto.semester

import java.time.LocalDateTime

data class SemesterResponseDTO(
    val id: Long,
    val isSndSemester: Boolean,
    val year: Int,
    val name: String,
    val acceptQuoteRequestsFrom: LocalDateTime,
    val acceptQuoteRequestsTo: LocalDateTime,
    val isCurrentSemester: Boolean,
    val isAcceptQuoteRequestsAvailable: Boolean,
)