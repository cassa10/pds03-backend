package ar.edu.unq.pds03backend.dto.csv

import com.opencsv.bean.CsvBindByName

data class CsvAcademyHistoryRequestDTO(
    @CsvBindByName(column = "DNI", required = true)
    var dni: String = "",
    @CsvBindByName(column = "Carrera", required = true)
    var codigoCarrera: String = "",
    @CsvBindByName(column = "Materia", required = true)
    var codigoMateria: String = "",
    @CsvBindByName(column = "Nombre", required = true)
    var nombreMateria: String = "",
    @CsvBindByName(column = "Fecha", required = true)
    var fecha: String = "",
    @CsvBindByName(column = "Resultado")
    var resultado: String = "",
    @CsvBindByName(column = "Nota")
    var nota: String = "",
    @CsvBindByName(column = "Forma Aprobación", required = true)
    var formaAprobacion: String = "",
    @CsvBindByName(column = "Crédito")
    var creditos: String = "",
    @CsvBindByName(column = "Acta_Promo", required = true)
    var actaPromo: String = "",
    @CsvBindByName(column = "Acta_examen")
    var actaExamen: String = "",
    @CsvBindByName(column = "Plan", required = true)
    var plan: String = "",
)