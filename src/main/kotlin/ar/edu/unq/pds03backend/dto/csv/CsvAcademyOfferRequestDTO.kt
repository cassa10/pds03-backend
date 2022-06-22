package ar.edu.unq.pds03backend.dto.csv

import com.opencsv.bean.CsvBindByName

data class CsvAcademyOfferRequestDTO(
    @CsvBindByName(column = "Materia", required = true)
    var materia: Int = 0,
    @CsvBindByName(column = "Comision", required = true)
    var comision: String = "",
    @CsvBindByName(column = "Profesores", required = true)
    var profesores: String = "",
    @CsvBindByName(column = "Cupo", required = true)
    var cupo: Int = 0,
    @CsvBindByName(column = "Horarios", required = true)
    var horarios: String = "",
)