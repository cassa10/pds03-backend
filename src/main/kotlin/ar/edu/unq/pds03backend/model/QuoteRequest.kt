package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "quote_requests")
class QuoteRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name="course_id")
    var course: Course,

    @ManyToOne
    @JoinColumn(name="student_id")
    var student: Student,

    @Column(nullable = false)
    var state: QuoteState,

    @Column(nullable = false)
    var comment: String
)
