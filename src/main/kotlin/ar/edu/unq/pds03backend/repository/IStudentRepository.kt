package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IStudentRepository : JpaRepository<Student, Long> {
    fun findByDni(dni: String): Optional<Student>
}