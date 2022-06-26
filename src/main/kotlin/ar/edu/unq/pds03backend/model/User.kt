package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role",
    discriminatorType = DiscriminatorType.INTEGER)
abstract class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(unique = true, nullable = false)
    val dni: String,

    @Column(nullable = false)
    var password: String,

    @Column(unique = true, nullable = false)
    val email: String,
)
{
    fun isStudent(): Boolean = false
    abstract fun role(): Role
}