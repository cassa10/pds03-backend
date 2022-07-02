package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "external_subjects")
class ExternalSubject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @OneToOne
    val subject: Subject,

    @Column(unique = true, nullable = false)
    val guarani_code: String
)
