package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.authentication.LoginRequestDTO
import ar.edu.unq.pds03backend.dto.authentication.LoginResponseDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.service.IAuthService
import ar.edu.unq.pds03backend.service.IJwtService
import ar.edu.unq.pds03backend.service.IPasswordService
import ar.edu.unq.pds03backend.service.IUserService
import ar.edu.unq.pds03backend.service.email.IEmailSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService(
    @Autowired private val userService: IUserService,
    @Autowired private val passwordService: IPasswordService,
    @Autowired private val jwtService: IJwtService,
    @Autowired private val emailSender: IEmailSender,
) : IAuthService {

    companion object {
        private const val PREFIX_BEARER = "Bearer "
    }

    override fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        val jwt = jwtService.generateToken(getUserAndValidatePassword(loginRequestDTO))
        return LoginResponseDTO.Mapper(jwt).map()
    }

    override fun getUserByToken(token: String): UserResponseDTO {
        var jwt = token
        try {
            if (token.contains(PREFIX_BEARER)) {
                jwt = token.drop(PREFIX_BEARER.length)
            }
            if (jwtService.isTokenExpired(jwt)) throw ExpiredTokenException()
            val userId = jwtService.getUserIdFromToken(jwt).toLong()
            return userService.getById(userId)
        } catch (e: Exception) {
            throw InvalidTokenException()
        }
    }

    override fun reestablishPassword(dni: String) {
        val user = getUserByDni(dni)
        if (!user.isStudent()) throw UserIsNotStudentException()
        val newPlainPassword = passwordService.generatePassword()
        user.password = passwordService.encryptPassword(newPlainPassword)
        userService.update(user)
        emailSender.sendNewPasswordMailToUser(user, newPlainPassword)
    }


    private fun getUserByDni(dni: String): User {
        return userService.findByDni(dni).orElseThrow { throw UserNotFoundException() }
    }

    private fun getUserAndValidatePassword(loginRequestDTO: LoginRequestDTO): User {
        val user = getUserByDni(loginRequestDTO.dni)
        if (!passwordService.matches(loginRequestDTO.password, user.password)) throw InvalidPassword()
        return user
    }

}