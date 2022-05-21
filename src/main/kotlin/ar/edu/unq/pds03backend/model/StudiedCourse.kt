package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "studied_courses")
class StudiedCourse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne
    @JoinColumn(name="course_id")
    val course: Course,

    @Column(nullable = true)
    val mark: Int?,

    @Column(nullable = false)
    val status: StatusStudiedCourse,

    @ManyToOne
    @JoinColumn(name="studiedDegree_id")
    val studiedDegree: StudiedDegree
)
{
    fun passed(): Boolean = status == StatusStudiedCourse.APPROVAL || status == StatusStudiedCourse.PROMOTED

    fun inProgress(): Boolean = status == StatusStudiedCourse.IN_PROGRESS
}
