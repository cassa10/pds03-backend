package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.*

interface IPersonRepository : JpaRepository<Person, Long> {
    fun findByEmailAndDni(email: String, dni: String): Optional<Person>
}