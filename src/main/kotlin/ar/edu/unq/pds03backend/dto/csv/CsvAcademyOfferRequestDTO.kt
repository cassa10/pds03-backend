package ar.edu.unq.pds03backend.dto.csv

import com.opencsv.bean.CsvBindByName

data class CsvAcademyOfferRequestDTO(
    @CsvBindByName(column = "Código", required = true)
    var codigoMateria: String = "",
    @CsvBindByName(column = "Actividad", required = true)
    var nombreMateria: String = "",
    @CsvBindByName(column = "Comisión", required = true)
    var comision: String = "",
    @CsvBindByName(column = "Modalidad", required = true)
    var modalidad: String = "",
    @CsvBindByName(column = "Ubicacion", required = true)
    var ubicacion: String = "",
    @CsvBindByName(column = "Banda Horaria y Aula", required = false)
    var horarios: String = "",
    
    var profesores: String = "",
    var cupo: Int = 30,
)