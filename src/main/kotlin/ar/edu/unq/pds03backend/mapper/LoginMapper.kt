package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.authentication.LoginResponseDTO
import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.model.Role

class LoginMapper : Mapper<User, LoginResponseDTO> {

    override fun toDTO(user: User): LoginResponseDTO {
        val role: Role = if (user.isStudent()) Role.STUDENT else Role.DIRECTOR
        return LoginResponseDTO(user.id!!, user.username, user.email, user.dni, role)
    }
}