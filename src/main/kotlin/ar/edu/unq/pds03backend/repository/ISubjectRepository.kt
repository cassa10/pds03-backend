package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface ISubjectRepository : JpaRepository<Subject, Long> {
    fun existsByName(name: String): Boolean
}