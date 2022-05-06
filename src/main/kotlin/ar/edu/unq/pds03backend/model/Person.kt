package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity(name="persons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role",
    discriminatorType = DiscriminatorType.INTEGER)
abstract class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(unique = true, nullable = false)
    val dni: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @OneToOne(mappedBy = "person")
    val user: User
)
{
    fun isStudent(): Boolean = false
}