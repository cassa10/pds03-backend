package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface IUserRepository : CrudRepository<User, Long> {
    fun findByEmailAndDni(email: String, dni: String): Optional<User>
}