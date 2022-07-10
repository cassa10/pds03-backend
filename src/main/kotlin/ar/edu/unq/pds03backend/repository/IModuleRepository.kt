package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Module
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IModuleRepository : JpaRepository<Module, Long> {
    fun findByName(name: String): Optional<Module>
}