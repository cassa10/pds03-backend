package ar.edu.unq.pds03backend.utils

import ar.edu.unq.pds03backend.model.Hour
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object HourHelper {
    fun parseLocalTime(time: String): LocalTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))

    fun parseHour(dayAndLocalTimes: String): Hour {
        val values = dayAndLocalTimes.split(" ")
        return Hour(from = parseLocalTime(values[1]), to = parseLocalTime(values[2]), day = parseDayOfWeek(values[0]))
    }

    private fun parseDayOfWeek(dayOfWeek: String): DayOfWeek {
        return when (dayOfWeek.uppercase(Locale.getDefault())) {
            "LUNES" -> DayOfWeek.MONDAY
            "MARTES" -> DayOfWeek.TUESDAY
            "MIÉRCOLES" -> DayOfWeek.WEDNESDAY
            "MIERCOLES" -> DayOfWeek.WEDNESDAY
            "JUEVES" -> DayOfWeek.THURSDAY
            "VIERNES" -> DayOfWeek.FRIDAY
            "SÁBADO" -> DayOfWeek.SATURDAY
            "SABADO" -> DayOfWeek.SATURDAY
            "DOMINGO" -> DayOfWeek.SUNDAY
            else -> throw IllegalArgumentException()
        }
    }
}
