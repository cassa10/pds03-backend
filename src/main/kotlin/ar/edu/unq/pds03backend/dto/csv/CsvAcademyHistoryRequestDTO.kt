package ar.edu.unq.pds03backend.dto.csv

import com.opencsv.bean.CsvBindByName

data class CsvAcademyHistoryRequestDTO(
    @CsvBindByName(column = "Legajo", required = true)
    var legajo: String = "",
    @CsvBindByName(column = "Carrera", required = true)
    var carrera: String = "",
    @CsvBindByName(column = "Materia", required = true)
    var materia: String = "",
    @CsvBindByName(column = "Nota", required = true)
    var nota: Int = 0,
    @CsvBindByName(column = "Resultado", required = true)
    var resultado: String = "",
)