package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.ConfigurableValidation
import ar.edu.unq.pds03backend.model.Validation
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IConfigurableValidationRepository: JpaRepository<ConfigurableValidation, Long> {
    fun findByValidation(validation: Validation): Optional<ConfigurableValidation>
}