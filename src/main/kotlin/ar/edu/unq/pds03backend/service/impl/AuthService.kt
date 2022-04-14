package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.LoginResponseDTO
import ar.edu.unq.pds03backend.mapper.LoginMapper
import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.service.IAuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired val userService: UserService) : IAuthService {
    override fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        val user = validate(loginRequestDTO)

        // TODO: Crear JWT

        return LoginMapper().toDTO(user)!!
    }

    private fun validate(loginRequestDTO: LoginRequestDTO): User {
        val user = userService.findById(1)

        if (!user.isPresent)
            // TODO: Custom Exception
            throw Exception("No se encontró usuario con email ${loginRequestDTO.email} y dni ${loginRequestDTO.dni}")

        return user.get()
    }
}