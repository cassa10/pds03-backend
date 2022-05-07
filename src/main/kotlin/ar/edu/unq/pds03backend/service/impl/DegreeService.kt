package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.degree.CreateDegreeRequestDTO
import ar.edu.unq.pds03backend.dto.degree.DegreeResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.exception.DegreeAlreadyExists
import ar.edu.unq.pds03backend.exception.DegreeNotFound
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

    override fun getAll(): List<DegreeResponseDTO> {
        val degrees = degreeRepository.findAll()

        return degrees.map { degree ->
            DegreeResponseDTO(
                id = degree.id!!,
                name = degree.name,
                acronym = degree.acronym,
                subjects = degree.subjects.map { subject ->
                    SubjectResponseDTO(
                        id = subject.id!!,
                        name = subject.name
                    )
                }

            )
        }
    }

    override fun update(id: Long, createDegreeRequestDTO: CreateDegreeRequestDTO) {
        val degree = degreeRepository.findById(id)

        if (!degree.isPresent) throw DegreeNotFound()

        val newDegree = degree.get()
        newDegree.name = createDegreeRequestDTO.name
        newDegree.acronym = createDegreeRequestDTO.acronym

        degreeRepository.save(newDegree)
    }

    override fun delete(id: Long) {
        val degree = degreeRepository.findById(id)

        if (!degree.isPresent) throw DegreeNotFound()

        degreeRepository.delete(degree.get())
    }
}