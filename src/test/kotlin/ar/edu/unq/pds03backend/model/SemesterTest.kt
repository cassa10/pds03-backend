package ar.edu.unq.pds03backend.model

import ar.edu.unq.pds03backend.utils.SemesterHelper
import junit.framework.TestCase.*
import org.junit.Test
import java.time.LocalDateTime

class SemesterTest {

    @Test
    fun `test semester hasInvalidAcceptRequestQuotesDates`(){
        val semester1 = defaultSemester()
        val semester2 = defaultSemester()
        semester1.acceptQuoteRequestsFrom = LocalDateTime.MAX
        semester1.acceptQuoteRequestsTo = LocalDateTime.MIN
        assertTrue(semester1.hasInvalidAcceptRequestQuotesDates())
        assertFalse(semester2.hasInvalidAcceptRequestQuotesDates())
    }

    @Test
    fun `test semester is current semester`(){
        val semesterNotCurrent = defaultSemester()
        semesterNotCurrent.year = 2000
        val semesterCurrent = defaultSemester()
        semesterCurrent.isSndSemester = SemesterHelper.currentIsSecondSemester
        semesterCurrent.year = SemesterHelper.currentYear
        assertEquals(semesterCurrent.id, 1.toLong())
        assertTrue(semesterCurrent.isCurrent())
        assertFalse(semesterNotCurrent.isCurrent())
    }

    @Test
    fun `test semester name by is second semester`(){
        val s1 = defaultSemester()
        s1.isSndSemester = false
        val s2 = defaultSemester()
        s2.isSndSemester = true
        assertEquals(s1.name(), "${s1.year} - Primer Cuatrimestre")
        assertEquals(s2.name(), "${s2.year} - Segundo Cuatrimestre")
    }

    private fun defaultSemester(): Semester {
        return Semester(id = 1, isSndSemester = true, year = 2000, acceptQuoteRequestsFrom = LocalDateTime.MIN, acceptQuoteRequestsTo = LocalDateTime.MAX)
    }
}