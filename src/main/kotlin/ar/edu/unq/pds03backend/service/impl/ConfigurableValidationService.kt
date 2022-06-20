package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.exception.ConfigurableValidationNotFoundException
import ar.edu.unq.pds03backend.model.ConfigurableValidation
import ar.edu.unq.pds03backend.repository.IConfigurableValidationRepository
import ar.edu.unq.pds03backend.service.IConfigurableValidationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConfigurableValidationService(
    @Autowired private val configurableValidationRepository: IConfigurableValidationRepository
): IConfigurableValidationService {
    override fun getById(id: Long): ConfigurableValidation {
        return getConfigurableValidation(id)
    }

    override fun getAll(): List<ConfigurableValidation> {
        return configurableValidationRepository.findAll()
    }

    @Transactional
    override fun update(id: Long, reqActive: Boolean) {
        val configurableValidation = getConfigurableValidation(id)
        if(configurableValidation.active != reqActive){
            configurableValidation.active = reqActive
            configurableValidationRepository.save(configurableValidation)
        }
    }

    private fun getConfigurableValidation(id: Long): ConfigurableValidation {
        val maybeConfigValidation = configurableValidationRepository.findById(id)
        if (maybeConfigValidation.isPresent.not()) throw ConfigurableValidationNotFoundException()
        return maybeConfigValidation.get()
    }
}