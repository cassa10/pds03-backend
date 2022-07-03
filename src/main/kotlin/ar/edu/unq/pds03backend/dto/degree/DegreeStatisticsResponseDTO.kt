package ar.edu.unq.pds03backend.dto.degree

import ar.edu.unq.pds03backend.model.Degree

class DegreeStatisticsResponseDTO(
    val id: Long,
    val name: String,
    val acronym: String,
    val pendingQuotes: Int,
    val approvedQuotes: Int,
    val revokedQuotes: Int,
){
    data class Mapper(
        private val degree: Degree,
        private val pendingQuotes: Int,
        private val approvedQuotes: Int,
        private val revokedQuotes: Int,
    ) {
        fun map():DegreeStatisticsResponseDTO = DegreeStatisticsResponseDTO(
            id = degree.id!!,
            name = degree.name,
            acronym = degree.acronym,
            pendingQuotes = pendingQuotes,
            approvedQuotes = approvedQuotes,
            revokedQuotes = revokedQuotes,
        )
    }
}