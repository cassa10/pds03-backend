package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.degree.DegreeRequestDTO
import ar.edu.unq.pds03backend.dto.degree.DegreeResponseDTO
import ar.edu.unq.pds03backend.model.Degree

interface IDegreeService {
    fun create(degreeRequestDTO: DegreeRequestDTO)
    fun getById(id: Long): DegreeResponseDTO
    fun getAll(): List<DegreeResponseDTO>
    fun update(id: Long, degreeRequestDTO: DegreeRequestDTO)
    fun delete(id: Long)
    fun findByAcronym(degreeAcronym: String): Degree
}