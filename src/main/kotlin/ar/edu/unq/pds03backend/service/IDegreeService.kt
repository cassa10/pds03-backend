package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.degree.DegreeRequestDTO
import ar.edu.unq.pds03backend.dto.degree.DegreeResponseDTO

interface IDegreeService {
    fun create(degreeRequestDTO: DegreeRequestDTO)
    fun getAll(): List<DegreeResponseDTO>
    fun update(id: Long, degreeRequestDTO: DegreeRequestDTO)
    fun delete(id: Long)
}