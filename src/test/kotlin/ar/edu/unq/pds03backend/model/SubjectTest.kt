package ar.edu.unq.pds03backend.model

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class SubjectTest {

    @Test
    fun `test add subject in prerequisite subject if only it not in that list with same id and is not itself`(){
        val subject = Subject.Builder().build()
        val id1: Long = 1
        val id2: Long = 2
        val prerequisiteSub1 = Subject.Builder().withId(id1).build()
        val prerequisiteSub2 = Subject.Builder().withId(id2).build()

        assertEquals(subject.prerequisiteSubjects.size, 0)
        subject.addPrerequisiteSubject(subject)
        assertEquals(subject.prerequisiteSubjects.size, 0)

        subject.addPrerequisiteSubject(prerequisiteSub1)
        assertEquals(subject.prerequisiteSubjects.size, 1)
        assertTrue(subject.prerequisiteSubjects.any {it.id == id1})

        subject.addPrerequisiteSubject(prerequisiteSub2)
        assertEquals(subject.prerequisiteSubjects.size, 2)
        assertTrue(subject.prerequisiteSubjects.any {it.id == id1})
        assertTrue(subject.prerequisiteSubjects.any {it.id == id2})

        subject.addPrerequisiteSubject(prerequisiteSub2)
        assertEquals(subject.prerequisiteSubjects.size, 2)
        assertTrue(subject.prerequisiteSubjects.any {it.id == id1})
        assertTrue(subject.prerequisiteSubjects.any {it.id == id2})
    }
}