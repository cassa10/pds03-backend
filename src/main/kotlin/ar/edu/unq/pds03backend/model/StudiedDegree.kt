package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "studied_degrees")
class StudiedDegree(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne
    @JoinColumn(name="degree_id")
    val degree: Degree,

    @ManyToOne
    @JoinColumn(name="student_id")
    val student: Student,

    @Column(nullable = false)
    val coefficient: Float,

    @OneToMany(mappedBy="studiedDegree")
    val studied_courses: Collection<StudiedCourse>
)
