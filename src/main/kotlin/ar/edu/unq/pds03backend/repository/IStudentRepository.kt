package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Student
import org.springframework.data.repository.CrudRepository

interface IStudentRepository : CrudRepository<Student, Long> {}