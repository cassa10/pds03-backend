package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.dto.LoginResponseDTO

class LoginMapper : Mapper<User, LoginResponseDTO> {
    override fun toDTO(entity: User): LoginResponseDTO {
        return LoginResponseDTO(entity.id!!, entity.username, entity.email, entity.dni!!, entity.role)
    }

    override fun fromDTO(dto: LoginResponseDTO): User {
        return User(dto.id, dto.username, dto.email, dto.dni, dto.role)
    }
}