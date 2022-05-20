package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(
    name = "semesters",
    uniqueConstraints = [UniqueConstraint(columnNames = ["is_snd_semester", "year"])]
)
class Semester(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "is_snd_semester", nullable = false)
    //True if is second semester of year
    val isSndSemester: Boolean,

    @Column(nullable = false)
    val year: Int,

    @Column(unique = true, nullable = false)
    val name: String
)
