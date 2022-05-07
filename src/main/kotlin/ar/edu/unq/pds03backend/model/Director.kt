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
    user: User
) : Person(id, firstName, lastName, dni, email, user)