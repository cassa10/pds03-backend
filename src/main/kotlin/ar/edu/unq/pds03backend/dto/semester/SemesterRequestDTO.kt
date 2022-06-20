package ar.edu.unq.pds03backend.dto.semester

import ar.edu.unq.pds03backend.model.Semester
import java.time.LocalDateTime
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class SemesterRequestDTO(
    @field:Min(value = 1900, message = "'year' mmin value is 1900")
    @field:Max(value = 4000, message = "'year' max value is 4000")
    val year: Int,
    @field:NotNull(message = "'isSndSemester' must not be null")
    val isSndSemester: Boolean,
    @field:NotNull(message = "'acceptQuoteRequestsFrom' must not be null")
    val acceptQuoteRequestsFrom: LocalDateTime,
    @field:NotNull(message = "'acceptQuoteRequestsTo' must not be null")
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
    @field:NotNull(message = "'acceptQuoteRequestsFrom' must not be null")
    val acceptQuoteRequestsFrom: LocalDateTime,
    @field:NotNull(message = "'acceptQuoteRequestsTo' must not be null")
    val acceptQuoteRequestsTo: LocalDateTime,
)