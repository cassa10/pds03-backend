package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.model.User
import java.util.*

interface IUserService {
    fun update(user: User)
    fun findByEmailAndDni(email: String, dni: String): Optional<User>
    fun getById(id: Long): UserResponseDTO
    fun findByDni(dni: String): Optional<User>
    fun createStudent(user: User): User
}