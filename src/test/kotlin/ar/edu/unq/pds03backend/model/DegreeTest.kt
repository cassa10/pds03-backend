package ar.edu.unq.pds03backend.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DegreeTest {

    @Test
    fun testGivenNewDegreeWhenGetSubjectsThenReturnAnEmptyCollection() {
        val degree = Degree.Builder().build()
        assertTrue(degree.subjects.isEmpty())
    }

    @Test
    fun testGivenNewDegreeWithNoSubjectsWhenAddANewSubjectThenReturnACollectionWithThatSubject() {
        val degree = Degree.Builder().build()
        val subject = Subject.Builder().withName("Subject 1").withModule(Module()).build()
        degree.addSubject(subject)
        assertEquals(degree.subjects.count(), 1)
        assertTrue(degree.subjects.contains(subject))
    }

    @Test
    fun testGivenNewDegreeWithNoSubjectsWhenAddTwiceANewSubjectThenReturnACollectionWithThatSubjectOnlyAddedOneTime() {
        val degree = Degree.Builder().build()
        val subject1 = Subject.Builder().withName("Subject 1").withModule(Module()).build()
        degree.addSubject(subject1)
        degree.addSubject(subject1)
        assertEquals(degree.subjects.count(), 1)
        assertTrue(degree.subjects.contains(subject1))
    }

    @Test
    fun testGivenNewDegreeWithNoSubjectsWhenAddTwoDifferentSubjectsThenReturnACollectionWithThatSubjects() {
        val degree = Degree.Builder().build()
        val subject1 = Subject.Builder().withName("Subject 1").withModule(Module()).build()
        val subject2 = Subject.Builder().withName("Subject 2").withModule(Module()).build()
        degree.addSubject(subject1)
        degree.addSubject(subject2)
        assertEquals(degree.subjects.count(), 2)
        assertTrue(degree.subjects.contains(subject1))
        assertTrue(degree.subjects.contains(subject2))
    }

    @Test
    fun testGivenNewDegreeWithThreeSubjectsWhenDeleteOneSubjectsThenReturnACollectionWithThatSubjectsWithoutDeletedOne() {
        val degree = Degree.Builder().build()
        val subject1 = Subject.Builder().withName("Subject 1").withModule(Module()).build()
        val subject2 = Subject.Builder().withName("Subject 2").withModule(Module()).build()
        val subject3 = Subject.Builder().withName("Subject 3").withModule(Module()).build()
        degree.addSubject(subject1)
        degree.addSubject(subject2)
        degree.addSubject(subject3)
        assertEquals(degree.subjects.count(), 3)

        degree.deleteSubject(subject2)
        assertEquals(degree.subjects.count(), 2)
        assertTrue(degree.subjects.contains(subject1))
        assertTrue(degree.subjects.contains(subject3))
        assertFalse(degree.subjects.contains(subject2))
    }
}