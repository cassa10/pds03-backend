package ar.edu.unq.pds03backend.utils

import ar.edu.unq.pds03backend.model.Hour
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object HourHelper {
    fun parseLocalTime(time: String): LocalTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))

    fun parseHours(daysAndLocalTimes: String): MutableList<Hour> =
        daysAndLocalTimes.split(" / ").map { dayAndLocalTimes -> parseHour(dayAndLocalTimes) }.toMutableList()

    private fun parseHour(dayAndLocalTimes: String): Hour {
        val values = dayAndLocalTimes.split(" ")
        val day = parseDayOfWeek(values[0])
        val fromHour = parseLocalTime(values[1])
        val toHour = parseLocalTime(values[3])
        return Hour(from = fromHour, to = toHour, day = day)
    }

    private fun parseDayOfWeek(dayOfWeek: String): DayOfWeek {
        return when (dayOfWeek.uppercase(Locale.getDefault())) {
            "LUN", "LUNES" -> DayOfWeek.MONDAY
            "MAR", "MARTES" -> DayOfWeek.TUESDAY
            "MIE", "MIÉRCOLES", "MIERCOLES" -> DayOfWeek.WEDNESDAY
            "JUE", "JUEVES" -> DayOfWeek.THURSDAY
            "VIE", "VIERNES" -> DayOfWeek.FRIDAY
            "SAB", "SÁBADO", "SABADO" -> DayOfWeek.SATURDAY
            "DOM", "DOMINGO" -> DayOfWeek.SUNDAY
            else -> throw IllegalArgumentException()
        }
    }
}
