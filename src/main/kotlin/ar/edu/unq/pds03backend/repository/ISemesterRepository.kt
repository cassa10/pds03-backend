package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Semester
import org.springframework.data.repository.CrudRepository

interface ISemesterRepository : CrudRepository<Semester, Long> {
}