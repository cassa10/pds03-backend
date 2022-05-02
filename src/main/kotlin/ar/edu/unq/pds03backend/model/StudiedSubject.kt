package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "studied_subjects")
class StudiedSubject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne
    @JoinColumn(name="student_id")
    val student: Student,

    @ManyToOne
    @JoinColumn(name="course_id")
    val course: Course,

    @Column(nullable = true)
    val mark: Int,

    @Column(unique = true, nullable = false)
    val status: StatusSubject
)
