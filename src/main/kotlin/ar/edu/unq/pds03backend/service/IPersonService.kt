package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.model.Person
import java.util.*

interface IPersonService {
    fun findByEmailAndDni(email: String, dni: String): Optional<Person>
}