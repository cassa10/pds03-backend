package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "subjects")
class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false)
    val name: String,

    @ManyToMany(mappedBy = "subjects", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val degree: Collection<Career>,
)
