package ar.edu.unq.pds03backend.dto.course

import ar.edu.unq.pds03backend.model.Hour
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.validation.annotation.Validated
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class HourRequestDTO(
    @field:NotBlank @field:Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "from time format must be 'HH:mm'") val from: String,
    @field:NotBlank @field:Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "to time format must be 'HH:mm'") val to: String,
    @field:NotNull val day: DayOfWeek
){
    @JsonIgnore
    fun getFromAsLocalTime(): LocalTime = parseLocalTime(from)
    @JsonIgnore
    fun getToAsLocalTime(): LocalTime = parseLocalTime(to)
    private fun parseLocalTime(time:String):LocalTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
}

class HourResponseDTO(
    val day: DayOfWeek,
    val from: String,
    val to: String,
){
    class Mapper(private val hour: Hour) {
        fun map(): HourResponseDTO = HourResponseDTO(
            day = hour.day,
            from = hour.getFromString(),
            to = hour.getToString(),
        )
    }
}