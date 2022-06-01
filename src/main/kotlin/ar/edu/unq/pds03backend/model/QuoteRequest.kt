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
    var state: QuoteState,

    @Column(nullable = false)
    val comment: String,

    @Column(nullable = false)
    var adminComment: String = ""
){
    fun accept() = state.accept(this)
    fun revoke() = state.revoke(this)
    fun rollbackToPending() = state.rollbackToPending(this)
}
