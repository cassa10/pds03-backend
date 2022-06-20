package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.news.NewRequestDTO
import ar.edu.unq.pds03backend.model.New


interface INewService {
    fun getAll(): List<New>
    fun get(id: Long): New
    fun create(dto: NewRequestDTO)
    fun update(id: Long, dto: NewRequestDTO)
    fun delete(id: Long)
}