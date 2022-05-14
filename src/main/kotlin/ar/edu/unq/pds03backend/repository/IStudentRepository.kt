package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface IStudentRepository : JpaRepository<Student, Long> {}