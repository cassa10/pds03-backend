package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(
    name = "semesters",
    uniqueConstraints = [UniqueConstraint(columnNames = ["semester", "year"])]
)
class Semester(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false)
    val semester: Boolean,

    @Column(nullable = false)
    val year: Int,

    @Column(unique = true, nullable = false)
    val name: String
)
