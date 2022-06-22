package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "external_degrees")
class ExternalDegree(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @OneToOne
    val degree: Degree,

    @Column(unique = true, nullable = false)
    val guarani_code: Int
    )
