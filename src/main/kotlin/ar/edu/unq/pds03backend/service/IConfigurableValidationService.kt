package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.model.ConfigurableValidation

interface IConfigurableValidationService {
    fun getById(id: Long): ConfigurableValidation
    fun getAll(): List<ConfigurableValidation>
    fun update(id: Long, reqActive: Boolean)
}