package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "studied_subject")
class StudiedSubject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne
    @JoinColumn(name="subject_id")
    val subject: Subject,

    @Column(nullable = true)
    val mark: Int,

    @Column(nullable = false)
    val status: StatusStudiedCourse,

    @ManyToOne
    @JoinColumn(name="studiedDegree_id")
    val studiedDegree: StudiedDegree
)
