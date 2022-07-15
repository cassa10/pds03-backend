package ar.edu.unq.pds03backend.utils

import ar.edu.unq.pds03backend.model.Semester
import java.time.LocalDate
import java.time.LocalDateTime

object SemesterHelper {
    val currentYear = LocalDate.now().year
    //FIXME: Seteo con mes 8 (Agosto) para no interferir con la demo (que puede ser hasta julio - mes 7).
    val currentIsSecondSemester = LocalDate.now().month.value >= 8

    fun isCurrentYear(year: Int): Boolean = year == currentYear
    fun isCurrentSecondSemester(isSecondSemester: Boolean): Boolean = isSecondSemester == currentIsSecondSemester

    fun defaultSemester(): Semester = Semester(id = null, year = 2000, isSndSemester = false, acceptQuoteRequestsFrom = LocalDateTime.MIN, acceptQuoteRequestsTo = LocalDateTime.MAX)
}