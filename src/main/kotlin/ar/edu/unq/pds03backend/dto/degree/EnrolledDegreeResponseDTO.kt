package ar.edu.unq.pds03backend.dto.degree

import ar.edu.unq.pds03backend.model.Degree
import ar.edu.unq.pds03backend.model.QualityState
import ar.edu.unq.pds03backend.model.RegistryState
import ar.edu.unq.pds03backend.model.StudiedDegree

class EnrolledDegreeResponseDTO(
    val id: Long?,
    val name: String,
    val acronym: String,
    val coefficient: Float,
    val registryState: RegistryState,
    val plan: String,
    val quality: QualityState,
    val location: String,
    val isRegular: Boolean,
) {
    class Mapper(
        private val degree: Degree,
        private val coefficient: Float,
        private val studiedDegree: StudiedDegree,
    ) {
        fun map(): EnrolledDegreeResponseDTO = EnrolledDegreeResponseDTO(
            id = degree.id,
            name = degree.name,
            acronym = degree.acronym,
            coefficient = coefficient,
            registryState = studiedDegree.registryState,
            plan = studiedDegree.plan,
            quality = studiedDegree.quality,
            location = studiedDegree.location,
            isRegular = studiedDegree.isRegular,
        )
    }
}