package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Subject
import org.springframework.data.repository.CrudRepository

interface ISubjectRepository : CrudRepository<Subject, Long> {
}