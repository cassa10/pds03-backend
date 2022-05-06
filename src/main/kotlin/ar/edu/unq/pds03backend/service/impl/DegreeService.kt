package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.CreateDegreeRequestDTO
import ar.edu.unq.pds03backend.exception.DegreeAlreadyExists
import ar.edu.unq.pds03backend.model.Degree
import ar.edu.unq.pds03backend.repository.IDegreeRepository
import ar.edu.unq.pds03backend.service.IDegreeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DegreeService(@Autowired private val degreeRepository: IDegreeRepository) : IDegreeService {
    override fun create(createDegreeRequestDTO: CreateDegreeRequestDTO) {
        val degree = degreeRepository.findByNameAndAcronym(createDegreeRequestDTO.name, createDegreeRequestDTO.acronym)

        if (degree.isPresent) throw DegreeAlreadyExists()

        degreeRepository.save(
            Degree(
                name = createDegreeRequestDTO.name,
                acronym = createDegreeRequestDTO.acronym,
                subjects = mutableListOf()
            )
        )
    }
}