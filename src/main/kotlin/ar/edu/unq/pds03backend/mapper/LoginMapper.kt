package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.dto.LoginResponseDTO
import ar.edu.unq.pds03backend.model.Role
import org.springframework.stereotype.Service

class LoginMapper : Mapper<User, LoginResponseDTO> {

    override fun toDTO(user: User): LoginResponseDTO {
        var role: Role = if (user.person.isStudent()) Role.STUDENT else Role.DIRECTOR
        return LoginResponseDTO(user.id!!, user.username, user.person.email, user.person.dni!!, role)
    }
}