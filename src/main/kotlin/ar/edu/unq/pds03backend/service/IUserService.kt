package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.model.User
import java.util.*

interface IUserService {
    fun findById(id: Long): Optional<User>
    fun findByEmailAndDni(email: String, dni: String): Optional<User>
}