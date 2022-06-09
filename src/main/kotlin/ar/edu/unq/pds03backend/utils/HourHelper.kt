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
        return when (dayOfWeek) {
            "Lunes" -> DayOfWeek.MONDAY
            "Martes" -> DayOfWeek.TUESDAY
            "Miércoles" -> DayOfWeek.WEDNESDAY
            "Jueves" -> DayOfWeek.THURSDAY
            "Viernes" -> DayOfWeek.FRIDAY
            "Sábado" -> DayOfWeek.SATURDAY
            else -> throw IllegalArgumentException()
        }
    }
}