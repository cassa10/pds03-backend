package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IUserRepository : JpaRepository<User, Long> {
    fun findByEmailAndDni(email: String, dni: String): Optional<User>
    fun findByDniOrEmail(dni: String, email: String): Optional<User>
    fun findByDni(dni: String): Optional<User>
}