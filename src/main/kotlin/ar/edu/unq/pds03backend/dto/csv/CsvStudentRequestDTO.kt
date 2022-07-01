package ar.edu.unq.pds03backend.dto.csv

import ar.edu.unq.pds03backend.model.Degree
import ar.edu.unq.pds03backend.model.Student
import com.opencsv.bean.CsvBindByName
import java.util.*

class CsvStudentRequestDTO(
    @CsvBindByName(column = "Apellido", required = true) var lastName: String = "",
    @CsvBindByName(column = "Nombre", required = true) var firstName: String = "",
    @CsvBindByName(column = "Documento", required = true) var dni: String = "",
    @CsvBindByName(column = "Propuesta", required = true) var degree: String = "",
    @CsvBindByName(column = "Plan", required = false) var _plan: String = "",
    @CsvBindByName(column = "Estado Inscr.", required = false) var _estado_inscr: String = "",
    @CsvBindByName(column = "Calidad", required = false) var _calidad: String = "",
    @CsvBindByName(column = "Regular", required = false) var _regular: String = "",
    @CsvBindByName(column = "Locación", required = false) var _locacion: String = "",
) {
    companion object {
        const val CSV_TPI_ID = "P"
        const val TPI_DEGREE_ACRONYM = "TPI"
        const val LI_DEGREE_ACRONYM = "LI"
    }

    fun email(): String = "${lastName.lowercase(Locale.getDefault())}_${dni}@alu.unq.edu.ar"

    fun legajo(): String = ""

    fun getDegreeAcronym(): String = when (degree) {
        CSV_TPI_ID -> TPI_DEGREE_ACRONYM
        else -> LI_DEGREE_ACRONYM
    }

    fun map(degree: Degree?): Student = Student(
        id = null,
        password = "",
        firstName = firstName,
        lastName = lastName,
        dni = dni,
        email = email(),
        legajo = legajo(),
        degree_histories = listOf(),
        enrolledCourses = mutableListOf(),
        enrolledDegrees = if(degree == null) mutableListOf() else mutableListOf(degree),
    )
}