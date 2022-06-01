package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Degree
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IDegreeRepository : JpaRepository<Degree, Long> {
    fun findByName(name: String): Optional<Degree>
    fun findByNameAndAcronym(name: String, acronym: String): Optional<Degree>
}