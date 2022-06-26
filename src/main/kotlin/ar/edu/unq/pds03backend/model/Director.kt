package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@DiscriminatorValue("2")
class Director(
    id: Long?,
    firstName: String,
    lastName: String,
    dni: String,
    email: String,
    password: String,
) : User(id=id,firstName=firstName,lastName=lastName,dni=dni,password=password,email=email){
    override fun role(): Role = Role.DIRECTOR
}