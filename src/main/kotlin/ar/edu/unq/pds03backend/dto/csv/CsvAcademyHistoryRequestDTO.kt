package ar.edu.unq.pds03backend.dto.csv

import com.opencsv.bean.CsvBindByName

data class CsvAcademyHistoryRequestDTO(
    @CsvBindByName(column = "dni", required = true)
    var dni: String = "",
    @CsvBindByName(column = "email", required = true)
    var email: String = "",
    @CsvBindByName(column = "firstName")
    var firstName: String = "",
    @CsvBindByName(column = "lastName")
    var lastName: String = "",
)