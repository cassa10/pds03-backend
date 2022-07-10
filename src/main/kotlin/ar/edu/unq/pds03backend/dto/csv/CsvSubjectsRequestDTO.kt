package ar.edu.unq.pds03backend.dto.csv

import ar.edu.unq.pds03backend.model.Degree
import com.opencsv.bean.CsvBindByPosition

class CsvSubjectsRequestDTO(
    @CsvBindByPosition(position = 0)
    var module: String = "",
    @CsvBindByPosition(position = 1)
    var externalSubjectId: String = "",
    @CsvBindByPosition(position = 2)
    var credits: String = "",
    @CsvBindByPosition(position = 3)
    var subjectName: String = "",
    @CsvBindByPosition(position = 4)
    var prerequisiteSubjects: String = "",
    //Use module A..D because csv change for each plan
    @CsvBindByPosition(position = 5)
    var prerequisiteModuleA_Credits: String = "",
    @CsvBindByPosition(position = 6)
    var prerequisiteModuleB_Credits: String = "",
    @CsvBindByPosition(position = 7)
    var prerequisiteModuleC_Credits: String = "",
    @CsvBindByPosition(position = 8)
    var prerequisiteModuleD_Credits: String = "",
) {

    private fun getPrerequisiteSubjectIds(): List<String> {
        if (prerequisiteSubjects.isEmpty()) {
            return listOf()
        }
        return prerequisiteSubjects.removeSurrounding("{", "}").split(";").map { it.trim() }
    }

    private fun isSubject(): Boolean = externalSubjectId.isNotEmpty()

    fun isModule(): Boolean = module.isNotEmpty()

    fun mapToCsvSubjectWithPrerequisite(degree: Degree): CsvSubjectWithPrerequisite? =
        if (isSubject())
            CsvSubjectWithPrerequisite(
                externalSubjectId = externalSubjectId,
                subjectName = subjectName,
                degree = degree,
                prerequisiteSubjectsExternalIds = getPrerequisiteSubjectIds(),
            )
        else null
}

//TODO: Add module
data class CsvSubjectWithPrerequisite(
    val externalSubjectId: String,
    val subjectName: String,
    val degree: Degree,
    val prerequisiteSubjectsExternalIds: List<String>,
)