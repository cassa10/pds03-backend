package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.model.User
import java.util.*

interface IUserService {
    fun findById(id: Long): Optional<User>

    // TODO: Agregar método para obtener usuario por email y dni
}