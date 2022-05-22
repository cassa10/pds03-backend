package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "quote_requests")
class QuoteRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name="course_id")
    val course: Course,

    @ManyToOne
    @JoinColumn(name="student_id")
    val student: Student,

    @Column(nullable = false)
    val state: QuoteState,

    @Column(nullable = false)
    val comment: String,

    @Column(nullable = true)
    var adminComment: String = ""
)
