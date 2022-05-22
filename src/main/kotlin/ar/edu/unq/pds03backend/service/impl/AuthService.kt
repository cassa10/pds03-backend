package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.authentication.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.authentication.LoginResponseDTO
import ar.edu.unq.pds03backend.exception.UserNotFoundException
import ar.edu.unq.pds03backend.mapper.LoginMapper
import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.service.IAuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired private val userService: UserService) : IAuthService {
    override fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        val person = validate(loginRequestDTO)

        // TODO: Crear JWT

        return LoginMapper().toDTO(person)
    }

    private fun validate(loginRequestDTO: LoginRequestDTO): User {
        val person = userService.findByEmailAndDni(loginRequestDTO.email, loginRequestDTO.dni)

        return person.orElseThrow { throw UserNotFoundException() }
    }
}