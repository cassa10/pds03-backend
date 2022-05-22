package ar.edu.unq.pds03backend.dto.degree

import ar.edu.unq.pds03backend.dto.subject.SimpleSubjectResponseDTO
import ar.edu.unq.pds03backend.model.Degree

class DegreeResponseDTO(
    val id: Long,
    val name: String,
    val acronym: String,
    val subjects: Collection<SimpleSubjectResponseDTO>
) {
    class Mapper(private val degree: Degree) {
        fun map(): DegreeResponseDTO =
            DegreeResponseDTO(
                id = degree.id!!,
                name = degree.name,
                acronym = degree.acronym,
                subjects = degree.subjects.map {
                    SimpleSubjectResponseDTO(
                        id = it.id!!,
                        name = it.name
                    )
                }
            )
    }
}