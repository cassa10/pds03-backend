package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Degree
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface IDegreeRepository : JpaRepository<Degree, Long> {
    fun findByName(name: String): Optional<Degree>
    fun findByNameAndAcronym(name: String, acronym: String): Optional<Degree>

    @Query("SELECT ed.degree FROM ExternalDegree ed WHERE ed.guarani_code = :guarani_code")
    fun findByGuaraniCode(guarani_code: Int): Optional<Degree>
    fun findByAcronym(degreeAcronym: String): Optional<Degree>
}