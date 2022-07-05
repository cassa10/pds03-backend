package ar.edu.unq.pds03backend.dto.csv

import ar.edu.unq.pds03backend.utils.CsvCourseHelper
import ar.edu.unq.pds03backend.utils.CsvStudentHelper
import com.opencsv.bean.CsvBindByName

data class CsvStudentCourseRegistrationRequestDTO(
    @CsvBindByName(column = "Nro. Identificación", required = true)
    var rawStudentDni: String = "",
    @CsvBindByName(column = "Comisión", required = true)
    var rawCourseExternalId: String = "",
    @CsvBindByName(column = "Estado Insc.", required = false)
    var registryState: String = "",
    @CsvBindByName(column = "Nro de Transacción", required = false)
    var transactionId: String = "",
) {
    fun getStudentDni(): String = CsvStudentHelper.getDni(rawStudentDni)

    fun getCourseExternalId(): String =
        CsvCourseHelper.getExternalId(rawCourseExternalId)
}