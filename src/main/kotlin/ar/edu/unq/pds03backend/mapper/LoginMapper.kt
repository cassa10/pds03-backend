package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.dto.LoginResponseDTO

class LoginMapper : Mapper<User, LoginResponseDTO> {
    override fun toDTO(user: User): LoginResponseDTO? {
        if (user === null)
            return null
        return LoginResponseDTO(user.id!!, user.username, user.email, user.dni!!, user.rol)
    }

    override fun fromDTO(dto: LoginResponseDTO): User? {
        if (dto === null)
            return null
        return User(dto.id, dto.username, dto.email, dto.dni, dto.rol)
    }
}