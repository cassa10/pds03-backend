package ar.edu.unq.pds03backend.dto.degree

class SimpleDegreeResponseDTO (
    val id: Long,
    val name: String,
    val acronym: String,
){
    class Mapper(private val id: Long,private val name: String, private val acronym: String){
        fun map():SimpleDegreeResponseDTO = SimpleDegreeResponseDTO(
            id = id,
            name = name,
            acronym = acronym,
        )
    }
}