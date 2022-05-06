package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(unique = true, nullable = false)
    val username: String,
    
    @OneToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    val person: Person
)
