package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.CreateDegreeRequestDTO

interface IDegreeService {
    fun create(createDegreeRequestDTO: CreateDegreeRequestDTO)
}