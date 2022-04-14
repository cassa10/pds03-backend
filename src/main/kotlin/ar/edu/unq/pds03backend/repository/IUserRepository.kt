package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.User
import org.springframework.data.repository.CrudRepository

interface IUserRepository : CrudRepository<User, Long> {
    // TODO: Agregar método que busque al usuario por email y dni
}