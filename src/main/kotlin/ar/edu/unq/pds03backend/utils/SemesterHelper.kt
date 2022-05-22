package ar.edu.unq.pds03backend.utils

import java.time.LocalDate

object SemesterHelper {
    val currentYear = LocalDate.now().year
    val currentSecondSemester = LocalDate.now().month.value >= 6

    fun isCurrentYear(year: Int): Boolean = year == currentYear

    fun isCurrentSecondSemester(isSecondSemester: Boolean): Boolean = isSecondSemester == currentSecondSemester
}