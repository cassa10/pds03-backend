package ar.edu.unq.pds03backend.dto.csv

import ar.edu.unq.pds03backend.model.Degree
import ar.edu.unq.pds03backend.model.QualityState
import ar.edu.unq.pds03backend.model.RegistryState
import ar.edu.unq.pds03backend.model.Student
import com.opencsv.bean.CsvBindByName

data class CsvStudentWithDegreeDTO(
    val student: Student,
    val degree: Degree,
    val plan: String,
    val registryState: RegistryState,
    val quality: QualityState,
    val isRegular: Boolean,
    val location: String,
)

class CsvStudentRequestDTO(
    @CsvBindByName(column = "Apellido", required = true) var lastName: String = "",
    @CsvBindByName(column = "Nombre", required = true) var firstName: String = "",
    @CsvBindByName(column = "Documento", required = true) var dni: String = "",
    @CsvBindByName(column = "Propuesta", required = true) var degree: String = "",
    @CsvBindByName(column = "Plan", required = true) var plan: String = "",
    @CsvBindByName(column = "Estado Inscr.", required = true) var registryState: String = "",
    @CsvBindByName(column = "Calidad", required = true) var quality: String = "",
    @CsvBindByName(column = "Regular", required = true) var regular: String = "",
    @CsvBindByName(column = "Locación", required = true) var location: String = "",
) {
    companion object {
        //const val REGISTRY_STATE_PENDING = "PENDIENTE"
        //const val CSV_LI_ID = "W"
        const val CSV_TPI_ID = "P"
        const val TPI_DEGREE_ACRONYM = "TPI"
        const val LI_DEGREE_ACRONYM = "LI"
        const val REGISTRY_STATE_ACCEPTED = "ACEPTADO"
        const val IS_REGULAR = "S"
        const val QUALITY_STATE_ACTIVE = "ACTIVO"
    }

    fun email(): String = "${lastName.lowercase()}_${dni}@alu.unq.edu.ar"

    fun legajo(): String = ""

    private fun getRegistryState(): RegistryState = when (registryState.uppercase()) {
        REGISTRY_STATE_ACCEPTED -> RegistryState.ACCEPTED
        else -> RegistryState.PENDING
    }

    private fun getQualityState(): QualityState = when (quality.uppercase()) {
        QUALITY_STATE_ACTIVE -> QualityState.ACTIVE
        else -> QualityState.PASSIVE
    }

    private fun getIsRegular(): Boolean = when (regular.uppercase()) {
        IS_REGULAR -> true
        else -> false
    }

    fun getDegreeAcronym(): String = when (degree.uppercase()) {
        CSV_TPI_ID -> TPI_DEGREE_ACRONYM
        else -> LI_DEGREE_ACRONYM
    }

    fun map(degree: Degree): CsvStudentWithDegreeDTO = CsvStudentWithDegreeDTO(
        student = mapStudent(degree),
        degree = degree,
        plan = plan,
        registryState = getRegistryState(),
        isRegular = getIsRegular(),
        location = location,
        quality = getQualityState(),
    )

    private fun mapStudent(degree: Degree): Student = Student(
        id = null,
        password = "",
        firstName = firstName,
        lastName = lastName,
        dni = dni,
        email = email(),
        legajo = legajo(),
        degreeHistories = mutableListOf(),
        enrolledCourses = mutableListOf(),
        enrolledDegrees = mutableListOf(degree),
    )
}