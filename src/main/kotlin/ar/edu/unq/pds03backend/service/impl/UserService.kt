package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.repository.IUserRepository
import ar.edu.unq.pds03backend.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(@Autowired private val userRepository: IUserRepository) : IUserService {

    override fun findById(id: Long): Optional<User> = userRepository.findById(id)

    override fun findByEmailAndDni(email: String, dni: String): Optional<User> = userRepository.findByEmailAndDni(email, dni)
}