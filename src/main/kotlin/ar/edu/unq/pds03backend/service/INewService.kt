package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.news.NewRequestDTO
import ar.edu.unq.pds03backend.model.New
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface INewService {
    fun getAll(): List<New>
    fun getAll(pageable: Pageable): Page<New>
    fun get(id: Long): New
    fun create(dto: NewRequestDTO)
    fun update(id: Long, dto: NewRequestDTO)
    fun delete(id: Long)
}