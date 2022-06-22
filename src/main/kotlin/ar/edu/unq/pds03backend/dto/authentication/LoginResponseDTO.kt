package ar.edu.unq.pds03backend.dto.authentication

class LoginResponseDTO(
    val jwt: String,
){
    class Mapper(private val jwt: String){
        fun map(): LoginResponseDTO = LoginResponseDTO(jwt)
    }
}