package ar.edu.unq.pds03backend.model

import junit.framework.Assert.*
import org.junit.Test
import java.time.LocalDate


class StudiedSubjectTest {

    @Test
    fun `test studied subject init`() {
        val idParam: Long = 1
        val subjectParam = Subject.Builder().build()
        val markParam = 6
        val statusParam = StatusStudiedCourse.IN_PROGRESS
        val dateParam = LocalDate.MIN
        val studiedDegreeParam = StudiedDegree.Builder().build()
        val studiedSubject =
            StudiedSubject(idParam, subjectParam, markParam, statusParam, dateParam, studiedDegreeParam)
        with(studiedSubject) {
            assertEquals(id, idParam)
            assertEquals(subject, subjectParam)
            assertEquals(mark, markParam)
            assertEquals(status, statusParam)
            assertEquals(date, dateParam)
            assertEquals(studiedDegree, studiedDegreeParam)
        }
    }

    @Test
    fun `test studied subject passed is true when is status APPROVED or PROMOTED`() {
        val studiedSubjectApproved = defaultStudiedSubjectWithStatus(StatusStudiedCourse.APPROVED)
        val studiedSubjectPendingApproval = defaultStudiedSubjectWithStatus(StatusStudiedCourse.PENDING_APPROVAL)
        val studiedSubjectDisapproved = defaultStudiedSubjectWithStatus(StatusStudiedCourse.DISAPPROVED)
        val studiedSubjectPromoted = defaultStudiedSubjectWithStatus(StatusStudiedCourse.PROMOTED)
        val studiedSubjectAbsent = defaultStudiedSubjectWithStatus(StatusStudiedCourse.ABSENT)
        val studiedSubjectPostponed = defaultStudiedSubjectWithStatus(StatusStudiedCourse.POSTPONED)
        val studiedSubjectVirtualPending = defaultStudiedSubjectWithStatus(StatusStudiedCourse.VIRTUAL_PENDING)
        val studiedSubjectAbsentExam = defaultStudiedSubjectWithStatus(StatusStudiedCourse.ABSENT_EXAM)
        val studiedSubjectInProgress = defaultStudiedSubjectWithStatus(StatusStudiedCourse.IN_PROGRESS)

        assertTrue(studiedSubjectApproved.passed())
        assertFalse(studiedSubjectPendingApproval.passed())
        assertFalse(studiedSubjectDisapproved.passed())
        assertTrue(studiedSubjectPromoted.passed())
        assertFalse(studiedSubjectAbsent.passed())
        assertFalse(studiedSubjectPostponed.passed())
        assertFalse(studiedSubjectVirtualPending.passed())
        assertFalse(studiedSubjectAbsentExam.passed())
        assertFalse(studiedSubjectInProgress.passed())
    }

    @Test
    fun `test studied subject inProgress is true when is status IN_PROGRESS or PENDING_APPROVAL`() {
        val studiedSubjectApproved = defaultStudiedSubjectWithStatus(StatusStudiedCourse.APPROVED)
        val studiedSubjectPendingApproval = defaultStudiedSubjectWithStatus(StatusStudiedCourse.PENDING_APPROVAL)
        val studiedSubjectDisapproved = defaultStudiedSubjectWithStatus(StatusStudiedCourse.DISAPPROVED)
        val studiedSubjectPromoted = defaultStudiedSubjectWithStatus(StatusStudiedCourse.PROMOTED)
        val studiedSubjectAbsent = defaultStudiedSubjectWithStatus(StatusStudiedCourse.ABSENT)
        val studiedSubjectPostponed = defaultStudiedSubjectWithStatus(StatusStudiedCourse.POSTPONED)
        val studiedSubjectVirtualPending = defaultStudiedSubjectWithStatus(StatusStudiedCourse.VIRTUAL_PENDING)
        val studiedSubjectAbsentExam = defaultStudiedSubjectWithStatus(StatusStudiedCourse.ABSENT_EXAM)
        val studiedSubjectInProgress = defaultStudiedSubjectWithStatus(StatusStudiedCourse.IN_PROGRESS)

        assertFalse(studiedSubjectApproved.inProgress())
        assertTrue(studiedSubjectPendingApproval.inProgress())
        assertFalse(studiedSubjectDisapproved.inProgress())
        assertFalse(studiedSubjectPromoted.inProgress())
        assertFalse(studiedSubjectAbsent.inProgress())
        assertFalse(studiedSubjectPostponed.inProgress())
        assertFalse(studiedSubjectVirtualPending.inProgress())
        assertFalse(studiedSubjectAbsentExam.inProgress())
        assertTrue(studiedSubjectInProgress.inProgress())
    }

    @Test
    fun `test studied subject translateState return their state translate`()
    {
        val studiedSubjectApproved = defaultStudiedSubjectWithStatus(StatusStudiedCourse.APPROVED)
        val studiedSubjectPendingApproval = defaultStudiedSubjectWithStatus(StatusStudiedCourse.PENDING_APPROVAL)
        val studiedSubjectDisapproved = defaultStudiedSubjectWithStatus(StatusStudiedCourse.DISAPPROVED)
        val studiedSubjectPromoted = defaultStudiedSubjectWithStatus(StatusStudiedCourse.PROMOTED)
        val studiedSubjectAbsent = defaultStudiedSubjectWithStatus(StatusStudiedCourse.ABSENT)
        val studiedSubjectPostponed = defaultStudiedSubjectWithStatus(StatusStudiedCourse.POSTPONED)
        val studiedSubjectVirtualPending = defaultStudiedSubjectWithStatus(StatusStudiedCourse.VIRTUAL_PENDING)
        val studiedSubjectAbsentExam = defaultStudiedSubjectWithStatus(StatusStudiedCourse.ABSENT_EXAM)
        val studiedSubjectInProgress = defaultStudiedSubjectWithStatus(StatusStudiedCourse.IN_PROGRESS)

        assertEquals(studiedSubjectApproved.translateState(), "Aprobado")
        assertEquals(studiedSubjectPendingApproval.translateState(), "Pendiente Aprobación")
        assertEquals(studiedSubjectDisapproved.translateState(), "Desaprobado")
        assertEquals(studiedSubjectPromoted.translateState(), "Promocionado")
        assertEquals(studiedSubjectAbsent.translateState(), "Ausente")
        assertEquals(studiedSubjectPostponed.translateState(), "Aplazo")
        assertEquals(studiedSubjectVirtualPending.translateState(), "Pendiente Virtual")
        assertEquals(studiedSubjectAbsentExam.translateState(), "Ausente de Examen")
        assertEquals(studiedSubjectInProgress.translateState(), "En curso")
    }

    private fun defaultStudiedSubjectWithStatus(status: StatusStudiedCourse): StudiedSubject =
        StudiedSubject(
            id = null,
            subject = Subject.Builder().build(),
            studiedDegree = StudiedDegree.Builder().build(),
            mark = null,
            status = status,
            date = LocalDate.MIN,
        )
}