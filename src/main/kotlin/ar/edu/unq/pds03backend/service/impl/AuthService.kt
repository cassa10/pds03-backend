package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.LoginResponseDTO
import ar.edu.unq.pds03backend.exception.UserNotFound
import ar.edu.unq.pds03backend.mapper.LoginMapper
import ar.edu.unq.pds03backend.model.Person
import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.service.IAuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired private val personService: PersonService) : IAuthService {
    override fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        val person = validate(loginRequestDTO)

        // TODO: Crear JWT

        return LoginMapper().toDTO(person.user)
    }

    private fun validate(loginRequestDTO: LoginRequestDTO): Person {
        val person = personService.findByEmailAndDni(loginRequestDTO.email, loginRequestDTO.dni)

        return person.orElseThrow { throw UserNotFound() }
    }
}