package ar.edu.unq.pds03backend.mapper

interface Mapper<E, D> {
    fun toDTO(entity: E): D
}