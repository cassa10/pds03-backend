package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "degrees")
class Degree(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(unique = true, nullable = false)
    val name: String,

    @Column(unique = true, nullable = false)
    val acronym: String,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "degree_subject",
        joinColumns = [JoinColumn(name = "degree_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "subject_id", referencedColumnName = "id")]
    )
    val subjects: Collection<Subject>
)