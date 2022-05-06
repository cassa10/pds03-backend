package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.model.Person
import ar.edu.unq.pds03backend.repository.IPersonRepository
import ar.edu.unq.pds03backend.service.IPersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class PersonService(@Autowired private val personRepository: IPersonRepository) : IPersonService {

    override fun findByEmailAndDni(email: String, dni: String): Optional<Person> = personRepository.findByEmailAndDni(email, dni)
}