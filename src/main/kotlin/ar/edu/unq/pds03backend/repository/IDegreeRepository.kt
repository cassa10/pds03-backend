package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Degree
import org.springframework.data.repository.CrudRepository
import java.util.*

interface IDegreeRepository : CrudRepository<Degree, Long> {
    fun findByNameAndAcronym(name: String, acronym: String): Optional<Degree>
}