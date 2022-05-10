package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.degree.DegreeRequestDTO
import ar.edu.unq.pds03backend.dto.degree.DegreeResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.exception.DegreeAlreadyExistsException
import ar.edu.unq.pds03backend.exception.DegreeNotFoundException
import ar.edu.unq.pds03backend.model.Degree
import ar.edu.unq.pds03backend.repository.IDegreeRepository
import ar.edu.unq.pds03backend.service.IDegreeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DegreeService(@Autowired private val degreeRepository: IDegreeRepository) : IDegreeService {
    override fun create(degreeRequestDTO: DegreeRequestDTO) {
        val degree = degreeRepository.findByNameAndAcronym(degreeRequestDTO.name, degreeRequestDTO.acronym)

        if (degree.isPresent) throw DegreeAlreadyExistsException()

        degreeRepository.save(
            Degree(
                name = degreeRequestDTO.name,
                acronym = degreeRequestDTO.acronym,
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

    override fun update(id: Long, degreeRequestDTO: DegreeRequestDTO) {
        val degree = degreeRepository.findById(id)

        if (!degree.isPresent) throw DegreeNotFoundException()

        val newDegree = degree.get()
        newDegree.name = degreeRequestDTO.name
        newDegree.acronym = degreeRequestDTO.acronym

        degreeRepository.save(newDegree)
    }

    override fun delete(id: Long) {
        val degree = degreeRepository.findById(id)

        if (!degree.isPresent) throw DegreeNotFoundException()

        degreeRepository.delete(degree.get())
    }
}