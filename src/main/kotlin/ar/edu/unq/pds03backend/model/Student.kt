package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "students")
class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(unique = true, nullable = false)
    val dni: String,

    @Column(unique = true, nullable = false)
    val legajo: String,

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @OneToMany(mappedBy = "student")
    val studiedDegrees: Collection<StudiedDegree>
)
