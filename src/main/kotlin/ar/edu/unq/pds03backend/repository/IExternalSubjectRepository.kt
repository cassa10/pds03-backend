package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.ExternalSubject
import org.springframework.data.jpa.repository.JpaRepository

interface IExternalSubjectRepository: JpaRepository<ExternalSubject, Long> {
}