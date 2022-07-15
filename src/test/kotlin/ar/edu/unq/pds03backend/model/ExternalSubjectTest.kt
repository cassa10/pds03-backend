package ar.edu.unq.pds03backend.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ExternalSubjectTest {

    @Test
    fun `test init external subject`(){
        val id: Long = 1
        val subject: Subject = Subject.Builder().build()
        val guaraniCode = "A1"
        val externalSubject = ExternalSubject(id, subject, guaraniCode)

        assertEquals(externalSubject.id, id)
        assertEquals(externalSubject.subject, subject)
        assertEquals(externalSubject.guarani_code, guaraniCode)
    }
}