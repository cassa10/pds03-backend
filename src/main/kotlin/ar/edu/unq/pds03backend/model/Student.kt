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

    @Column(nullable = false)
    val coefficient: Float,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "student_career",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "career_id", referencedColumnName = "id")]
    )
    val registered_careers: Collection<Career>,

    @OneToMany(mappedBy="student")
    val studied_subjects: Collection<StudiedSubject>
)
