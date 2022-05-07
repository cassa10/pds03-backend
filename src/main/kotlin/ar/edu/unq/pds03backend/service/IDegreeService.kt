package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.degree.CreateDegreeRequestDTO
import ar.edu.unq.pds03backend.dto.degree.DegreeResponseDTO

interface IDegreeService {
    fun create(createDegreeRequestDTO: CreateDegreeRequestDTO)
    fun getAll(): List<DegreeResponseDTO>
    fun update(id: Long, createDegreeRequestDTO: CreateDegreeRequestDTO)
    fun delete(id: Long)
}