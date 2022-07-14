package ar.edu.unq.pds03backend.model

import junit.framework.Assert.*
import org.junit.Test

class StudiedDegreeTest {

    @Test
    fun `test studied degree init`() {
        val id1: Long = 1
        val degree1 = Degree.Builder().build()
        val student1 = Student.Builder().build()
        val coefficient1 = 2f
        val registryState1 = RegistryState.PENDING
        val plan1 = "2000"
        val quality1 = QualityState.PASSIVE
        val location1 = "Beraza"
        val isRegular1 = false
        val studiedSubjects1: Collection<StudiedSubject> = listOf()
        val id2: Long = 2
        val degree2 = Degree.Builder().build()
        val student2 = Student.Builder().build()
        val coefficient2 = 9f
        val registryState2 = RegistryState.ACCEPTED
        val plan2 = "2020"
        val quality2 = QualityState.ACTIVE
        val location2 = "Berlin"
        val isRegular2 = true
        val studiedSubjects2: Collection<StudiedSubject> = listOf()
        val studiedDegree1 = StudiedDegree(
            id = id1,
            degree = degree1,
            student = student1,
            coefficient = coefficient1,
            registryState = registryState1,
            plan = plan1,
            quality = quality1,
            isRegular = isRegular1,
            location = location1,
            studied_subjects = studiedSubjects1,
        )
        val studiedDegree2 =
            StudiedDegree.Builder().withId(id2).withDegree(degree2).withStudent(student2).withCoefficient(coefficient2)
                .withRegistryState(registryState2).withPlan(plan2).withQuality(quality2).withLocation(location2)
                .withIsRegular(isRegular2).withStudiedSubjects(studiedSubjects2).build()

        with(studiedDegree1){
            assertEquals(id, id1)
            assertEquals(degree, degree1)
            assertEquals(student, student1)
            assertEquals(coefficient, coefficient1)
            assertEquals(registryState, registryState1)
            assertEquals(plan, plan1)
            assertEquals(quality, quality1)
            assertEquals(isRegular, isRegular1)
            assertEquals(location, location1)
            assertEquals(studied_subjects, studiedSubjects1)
        }

        with(studiedDegree2){
            assertEquals(id, id2)
            assertEquals(degree, degree2)
            assertEquals(student, student2)
            assertEquals(coefficient, coefficient2)
            assertEquals(registryState, registryState2)
            assertEquals(plan, plan2)
            assertEquals(quality, quality2)
            assertEquals(isRegular, isRegular2)
            assertEquals(location, location2)
            assertEquals(studied_subjects, studiedSubjects2)
        }
    }

    @Test
    fun `test studied degree quote request condition always true only when registry state is accepted, quality state is active and is regular`(){
        val studiedDegree1 = StudiedDegree.Builder().withRegistryState(RegistryState.ACCEPTED).withQuality(QualityState.ACTIVE).withIsRegular(true).build()
        val studiedDegree2 = StudiedDegree.Builder().withRegistryState(RegistryState.ACCEPTED).withQuality(QualityState.ACTIVE).withIsRegular(false).build()
        val studiedDegree3 = StudiedDegree.Builder().withRegistryState(RegistryState.ACCEPTED).withQuality(QualityState.PASSIVE).withIsRegular(true).build()
        val studiedDegree4 = StudiedDegree.Builder().withRegistryState(RegistryState.ACCEPTED).withQuality(QualityState.PASSIVE).withIsRegular(false).build()
        val studiedDegree5 = StudiedDegree.Builder().withRegistryState(RegistryState.PENDING).withQuality(QualityState.ACTIVE).withIsRegular(true).build()
        val studiedDegree6 = StudiedDegree.Builder().withRegistryState(RegistryState.PENDING).withQuality(QualityState.ACTIVE).withIsRegular(false).build()
        val studiedDegree7 = StudiedDegree.Builder().withRegistryState(RegistryState.PENDING).withQuality(QualityState.PASSIVE).withIsRegular(true).build()
        val studiedDegree8 = StudiedDegree.Builder().withRegistryState(RegistryState.PENDING).withQuality(QualityState.PASSIVE).withIsRegular(false).build()
        assertTrue(studiedDegree1.isQuoteRequestCondition())
        listOf(studiedDegree2,studiedDegree3,studiedDegree4,studiedDegree5,studiedDegree6,studiedDegree7, studiedDegree8).forEach {
            assertFalse(it.isQuoteRequestCondition())
        }
    }
}