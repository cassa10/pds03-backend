package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.authentication.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.authentication.LoginResponseDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.exception.ExpiredTokenException
import ar.edu.unq.pds03backend.exception.InvalidPassword
import ar.edu.unq.pds03backend.exception.InvalidTokenException
import ar.edu.unq.pds03backend.exception.UserNotFoundException
import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.service.IAuthService
import ar.edu.unq.pds03backend.service.IJwtService
import ar.edu.unq.pds03backend.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    @Autowired private val userService: IUserService,
    @Autowired private val passwordEncoder: PasswordEncoder,
    @Autowired private val jwtService: IJwtService,
) : IAuthService {

    companion object {
        private const val PREFIX_BEARER = "Bearer "
    }
    override fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        val jwt = jwtService.generateToken(getUser(loginRequestDTO))
        return LoginResponseDTO.Mapper(jwt).map()
    }

    override fun getUserByToken(token: String): UserResponseDTO {
        var jwt = token
        try {
            if (token.contains(PREFIX_BEARER)){
                jwt = token.drop(PREFIX_BEARER.length)
            }
            if (jwtService.isTokenExpired(jwt)) throw ExpiredTokenException()
            val userId = jwtService.getUserIdFromToken(jwt).toLong()
            return userService.getById(userId)
        }catch (e: Exception) {
            throw InvalidTokenException()
        }
    }

    private fun getUser(loginRequestDTO: LoginRequestDTO): User {
        val user = userService.findByDni(loginRequestDTO.dni).orElseThrow { throw UserNotFoundException() }
        if (!passwordEncoder.matches(loginRequestDTO.password, user.password)) throw InvalidPassword()
        return user
    }

}