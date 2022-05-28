package ar.edu.unq.pds03backend.dto.semester

import ar.edu.unq.pds03backend.model.Semester
import java.time.LocalDateTime
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class SemesterRequestDTO(
    @field:Min(value = 2000)
    @field:Max(value = 3000)
    val year: Int,
    @field:NotNull
    val isSndSemester: Boolean,
    @field:NotNull
    val acceptQuoteRequestsFrom: LocalDateTime,
    @field:NotNull
    val acceptQuoteRequestsTo: LocalDateTime,
) {
    fun mapToSemester(): Semester = Semester(
        year = year,
        isSndSemester = isSndSemester,
        acceptQuoteRequestsFrom = acceptQuoteRequestsFrom,
        acceptQuoteRequestsTo = acceptQuoteRequestsTo,
    )
}

data class UpdateSemesterRequestDTO(
    @field:NotNull
    val acceptQuoteRequestsFrom: LocalDateTime,
    @field:NotNull
    val acceptQuoteRequestsTo: LocalDateTime,
)