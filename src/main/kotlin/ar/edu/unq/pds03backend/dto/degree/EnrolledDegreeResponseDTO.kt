package ar.edu.unq.pds03backend.dto.degree

import ar.edu.unq.pds03backend.model.Degree

class EnrolledDegreeResponseDTO(
    val id: Long?,
    val name: String,
    val acronym: String,
    val coefficient: Float,
) {
    class Mapper(private val degree: Degree, private val coefficient: Float) {
        fun map(): EnrolledDegreeResponseDTO = EnrolledDegreeResponseDTO(
            id = degree.id,
            name = degree.name,
            acronym = degree.acronym,
            coefficient = coefficient,
        )
    }
}