package ar.edu.unq.pds03backend.model

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class QuoteRequestTest {

    @Test
    fun `test quote request init`(){
        val id: Long = 1
        val createdOn: LocalDateTime = LocalDateTime.MIN
        val course: Course = Course.Builder().build()
        val student: Student = Student.Builder().build()
        val state: QuoteState = QuoteState.PENDING
        val comment = "a"
        val adminComment = "b"
        val id2: Long = 1
        val createdOn2: LocalDateTime = LocalDateTime.MAX
        val course2: Course = Course.Builder().build()
        val student2: Student = Student.Builder().build()
        val state2: QuoteState = QuoteState.APPROVED
        val comment2 = "a2"
        val adminComment2 = "b2"
        val quoteRequest = QuoteRequest.Builder()
            .withId(id)
            .withCreatedOn(createdOn)
            .withCourse(course)
            .withState(state)
            .withStudent(student)
            .withComment(comment)
            .withAdminComment(adminComment)
        val quoteRequest2 = QuoteRequest(id2, createdOn2, course2, student2, state2, comment2, adminComment2)
        assertEquals(quoteRequest.id, id)
        assertEquals(quoteRequest.createdOn, createdOn)
        assertEquals(quoteRequest.course, course)
        assertEquals(quoteRequest.student, student)
        assertEquals(quoteRequest.state, state)
        assertEquals(quoteRequest.comment, comment)
        assertEquals(quoteRequest.adminComment, adminComment)

        assertEquals(quoteRequest2.id, id2)
        assertEquals(quoteRequest2.createdOn, createdOn2)
        assertEquals(quoteRequest2.course, course2)
        assertEquals(quoteRequest2.student, student2)
        assertEquals(quoteRequest2.state, state2)
        assertEquals(quoteRequest2.comment, comment2)
        assertEquals(quoteRequest2.adminComment, adminComment2)
    }

    @Test
    fun `test quote request accept change state to APPROVED only with states PENDING or AUTOAPPROVED and in both cases student add quote request course in their enrolledCourses`(){
        val course = Course.Builder().build()
        val student: Student = mockk{
            every { addEnrolledCourse(course) } returns Unit
        }
        val quoteRequestPending = QuoteRequest.Builder().withState(QuoteState.PENDING).withCourse(course).withStudent(student).build()
        val quoteRequestApproved = QuoteRequest.Builder().withState(QuoteState.APPROVED).withCourse(course).withStudent(student).build()
        val quoteRequestRevoked = QuoteRequest.Builder().withState(QuoteState.REVOKED).withCourse(course).withStudent(student).build()
        val quoteRequestAutoApproved = QuoteRequest.Builder().withState(QuoteState.AUTOAPPROVED).withCourse(course).withStudent(student).build()

        quoteRequestApproved.accept()
        quoteRequestRevoked.accept()
        verify(exactly = 0) { student.addEnrolledCourse(course) }
        quoteRequestPending.accept()
        verify(exactly = 1) { student.addEnrolledCourse(course) }
        quoteRequestAutoApproved.accept()
        verify(exactly = 2) { student.addEnrolledCourse(course) }
        assertEquals(quoteRequestApproved.state, QuoteState.APPROVED)
        assertEquals(quoteRequestRevoked.state, QuoteState.REVOKED)
        assertEquals(quoteRequestPending.state, QuoteState.APPROVED)
        assertEquals(quoteRequestAutoApproved.state, QuoteState.APPROVED)
    }

    @Test
    fun `test quote request revoke change state to REVOKED only with states PENDING or AUTOAPPROVED`(){
        val quoteRequestPending = QuoteRequest.Builder().withState(QuoteState.PENDING).build()
        val quoteRequestApproved = QuoteRequest.Builder().withState(QuoteState.APPROVED).build()
        val quoteRequestRevoked = QuoteRequest.Builder().withState(QuoteState.REVOKED).build()
        val quoteRequestAutoApproved = QuoteRequest.Builder().withState(QuoteState.AUTOAPPROVED).build()

        quoteRequestApproved.revoke()
        quoteRequestRevoked.revoke()
        quoteRequestPending.revoke()
        quoteRequestAutoApproved.revoke()

        assertEquals(quoteRequestApproved.state, QuoteState.APPROVED)
        assertEquals(quoteRequestRevoked.state, QuoteState.REVOKED)
        assertEquals(quoteRequestPending.state, QuoteState.REVOKED)
        assertEquals(quoteRequestAutoApproved.state, QuoteState.REVOKED)
    }

    @Test
    fun `test quote request rollbackPending change state to PENDING only with states APPROVED or REVOKED and student delete quote request course in their enrolled courses when is APPROVED`(){
        val course = Course.Builder().build()
        val student: Student = mockk{
            every { deleteEnrolledCourse(course) } returns true
        }
        val quoteRequestPending = QuoteRequest.Builder().withState(QuoteState.PENDING).withCourse(course).withStudent(student).build()
        val quoteRequestApproved = QuoteRequest.Builder().withState(QuoteState.APPROVED).withCourse(course).withStudent(student).build()
        val quoteRequestRevoked = QuoteRequest.Builder().withState(QuoteState.REVOKED).withCourse(course).withStudent(student).build()
        val quoteRequestAutoApproved = QuoteRequest.Builder().withState(QuoteState.AUTOAPPROVED).withCourse(course).withStudent(student).build()

        quoteRequestPending.rollbackToPending()
        quoteRequestAutoApproved.rollbackToPending()
        quoteRequestRevoked.rollbackToPending()
        verify(exactly = 0) { student.deleteEnrolledCourse(course) }

        quoteRequestApproved.rollbackToPending()
        verify(exactly = 1) { student.deleteEnrolledCourse(course) }


        assertEquals(quoteRequestApproved.state, QuoteState.PENDING)
        assertEquals(quoteRequestRevoked.state, QuoteState.PENDING)
        assertEquals(quoteRequestPending.state, QuoteState.PENDING)
        assertEquals(quoteRequestAutoApproved.state, QuoteState.AUTOAPPROVED)
    }

}