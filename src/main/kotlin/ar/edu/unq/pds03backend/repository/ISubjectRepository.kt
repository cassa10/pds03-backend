package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface ISubjectRepository : JpaRepository<Subject, Long> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): Optional<Subject>

    @Query("SELECT es.subject FROM ExternalSubject es WHERE es.guarani_code = :guarani_code")
    fun findSubjectByGuaraniCode(guarani_code: Int): Optional<Subject>
}