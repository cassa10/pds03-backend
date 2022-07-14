package ar.edu.unq.pds03backend.model

import java.time.LocalDateTime
import javax.persistence.*;

@Entity
@Table(name = "quote_requests")
class QuoteRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "created_on", nullable = false)
    val createdOn: LocalDateTime,

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
    companion object {
        const val createdOnFieldName = "createdOn"
    }
    fun accept() = state.accept(this)
    fun revoke() = state.revoke(this)
    fun rollbackToPending() = state.rollbackToPending(this)

    data class Builder(
        var id: Long? = null,
        var createdOn: LocalDateTime = LocalDateTime.MIN,
        var course: Course = Course.Builder().build(),
        var student: Student = Student.Builder().build(),
        var state: QuoteState = QuoteState.PENDING,
        var comment: String = "",
        var adminComment: String = ""
    ){
        fun build(): QuoteRequest = QuoteRequest(
            id = id,
            createdOn = createdOn,
            course = course,
            student = student,
            state = state,
            comment = comment,
            adminComment = adminComment,
        )

        fun withId(id: Long) = apply { this.id = id}
        fun withCreatedOn(createdOn: LocalDateTime) = apply { this.createdOn = createdOn}
        fun withCourse(course: Course) = apply { this.course = course}
        fun withStudent(student: Student) = apply { this.student = student}
        fun withState(state: QuoteState) = apply { this.state = state}
        fun withComment(comment: String) = apply { this.comment = comment}
        fun withAdminComment(adminComment: String) = apply { this.adminComment = adminComment}
    }
}
