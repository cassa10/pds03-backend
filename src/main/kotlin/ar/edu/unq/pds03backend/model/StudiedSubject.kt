package ar.edu.unq.pds03backend.model

import java.time.LocalDate
import javax.persistence.*;

@Entity
@Table(name = "studied_subjects")
class StudiedSubject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name="subject_id")
    val subject: Subject,

    @Column(nullable = true)
    var mark: Int?,

    @Column(nullable = false)
    var status: StatusStudiedCourse,

    @Column(nullable = false)
    var date: LocalDate,

    @ManyToOne
    @JoinColumn(name="studiedDegree_id")
    val studiedDegree: StudiedDegree
)
{
    fun passed(): Boolean = status.passed()

    fun inProgress(): Boolean = status.inProgress()

    fun translateState(): String = status.translate()
}
