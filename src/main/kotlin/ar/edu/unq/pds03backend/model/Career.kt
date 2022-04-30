package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "careers")
class Career(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Column(unique = true, nullable = false)
    val name: String,
    @ManyToMany(cascade = arrayOf(CascadeType.ALL))
    @JoinTable(name = "career_subject",
        joinColumns = arrayOf(JoinColumn(name = "career_id", referencedColumnName = "id")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "subject_id", referencedColumnName = "id")))
    val subjects: Collection<Subject>
)