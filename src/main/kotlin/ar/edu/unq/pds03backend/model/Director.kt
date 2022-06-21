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
    username: String,
    password: String,
) : User(id, firstName, lastName, dni, email, username, password){
    override fun role(): Role = Role.DIRECTOR
}