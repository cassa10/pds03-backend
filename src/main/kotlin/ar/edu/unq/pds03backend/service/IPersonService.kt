package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.model.Person
import java.util.*

interface IPersonService {
    fun findByEmailAndDni(email: String, dni: String): Optional<Person>
    fun getById(id: Long): UserResponseDTO
}