package ar.edu.unq.pds03backend.dto.course

import java.time.DayOfWeek

class HourRequestDTO(
    val from: String,
    val to: String,
    val day: DayOfWeek
)