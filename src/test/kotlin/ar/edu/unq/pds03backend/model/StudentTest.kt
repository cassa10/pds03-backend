package ar.edu.unq.pds03backend.model

import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.*
import org.junit.Test

class StudentTest {

    @Test
    fun `test student init`() {
        val id1: Long = 1
        val firstName1 = "f"
        val lastName1 = "l"
        val dni1 = "123"
        val email1 = "asd"
        val password1 = "p"
        val legajo1 = "legajo"
        val enrolledDegrees1: MutableCollection<Degree> = mutableListOf()
        val degreeHistories1: MutableCollection<StudiedDegree> = mutableListOf()
        val enrolledCourses1: MutableCollection<Course> = mutableListOf()

        val id2: Long = 2
        val firstName2 = "f2"
        val lastName2 = "l2"
        val dni2 = "1232"
        val email2 = "asd2"
        val password2 = "p2"
        val legajo2 = "legajo2"
        val enrolledDegrees2: MutableCollection<Degree> = mutableListOf()
        val degreeHistories2: MutableCollection<StudiedDegree> = mutableListOf()
        val enrolledCourses2: MutableCollection<Course> = mutableListOf()


        val student1 = Student(
            id1,
            firstName1,
            lastName1,
            dni1,
            email1,
            password1,
            legajo1,
            enrolledDegrees1,
            degreeHistories1,
            enrolledCourses1
        )
        val student2 = Student.Builder().withId(id2).withFirstName(firstName2).withLastname(lastName2).withDni(dni2)
            .withEmail(email2).withPassword(password2).withLegajo(legajo2).withEnrolledDegrees(enrolledDegrees2)
            .withDegreeHistories(degreeHistories2).withEnrolledCourses(enrolledCourses2).build()
        with(student1) {
            assertEquals(id, id1)
            assertEquals(firstName, firstName1)
            assertEquals(lastName, lastName1)
            assertEquals(dni, dni1)
            assertEquals(email, email1)
            assertEquals(password, password1)
            assertEquals(legajo, legajo1)
            assertEquals(enrolledDegrees, enrolledDegrees1)
            assertEquals(degreeHistories, degreeHistories1)
            assertEquals(enrolledCourses, enrolledCourses1)
            assertEquals(role(), Role.STUDENT)
            assertTrue(isStudent())
        }

        with(student2) {
            assertEquals(id, id2)
            assertEquals(firstName, firstName2)
            assertEquals(lastName, lastName2)
            assertEquals(dni, dni2)
            assertEquals(email, email2)
            assertEquals(password, password2)
            assertEquals(legajo, legajo2)
            assertEquals(enrolledDegrees, enrolledDegrees2)
            assertEquals(degreeHistories, degreeHistories2)
            assertEquals(enrolledCourses, enrolledCourses2)
            assertEquals(role(), Role.STUDENT)
            assertTrue(isStudent())
        }
    }

    @Test
    fun `test student isStudyingOrEnrolled subject returns true if student has that subject in enrolled courses or any studied subject of any studied degree and can course subject returns opposite`() {
        val subjectA = Subject.Builder().build()
        val subjectB = Subject.Builder().build()

        val studiedSubject1: StudiedSubject = mockk {
            every { subject } returns subjectA
            every { inProgress() } returns true
        }
        val studiedSubject2: StudiedSubject = mockk {
            every { subject } returns subjectB
            every { inProgress() } returns false
        }
        val studiedDegree1: StudiedDegree = mockk { every { studied_subjects } returns listOf(studiedSubject1) }
        val studiedDegree2: StudiedDegree = mockk { every { studied_subjects } returns listOf(studiedSubject2) }

        val enrolledCourse = Course.Builder().withSubject(subjectA).build()
        val student0 = Student.Builder().withEnrolledCourses(mutableListOf(enrolledCourse)).build()
        val student1 = Student.Builder().withDegreeHistories(mutableListOf(studiedDegree1)).build()
        val student2 = Student.Builder().withDegreeHistories(mutableListOf(studiedDegree2)).build()

        assertTrue(student0.isStudyingOrEnrolled(subjectA))
        assertFalse(student0.isStudyingOrEnrolled(subjectB))
        assertFalse(student0.canCourseSubject(subjectA))
        assertTrue(student0.canCourseSubject(subjectB))

        assertTrue(student1.isStudyingOrEnrolled(subjectA))
        assertFalse(student1.isStudyingOrEnrolled(subjectB))
        assertFalse(student1.canCourseSubject(subjectA))
        assertTrue(student1.canCourseSubject(subjectB))

        assertFalse(student2.isStudyingOrEnrolled(subjectA))
        assertFalse(student2.isStudyingOrEnrolled(subjectB))
        assertTrue(student2.canCourseSubject(subjectA))
        assertTrue(student2.canCourseSubject(subjectB))
    }

    @Test
    fun `test student is studying any degree of a collection of degrees`() {
        val degreeId: Long = 1
        val degree1 = Degree.Builder().withId(degreeId).build()
        val degree2 = Degree.Builder().withId(5).build()

        val student1 = Student.Builder().withEnrolledDegrees(mutableListOf()).build()
        val student2 = Student.Builder().withEnrolledDegrees(mutableListOf(degree1)).build()

        assertFalse(student1.isStudyingAnyDegree(listOf(degree1)))

        assertTrue(student2.isStudyingAnyDegree(listOf(degree1)))
        assertFalse(student2.isStudyingAnyDegree(listOf(degree2)))
        assertFalse(student2.isStudyingAnyDegree(listOf()))
    }

    @Test
    fun `test student add a course in enrolled courses only if not exist in their list`() {
        val courseId1: Long = 1
        val courseId2: Long = 2
        val course1 = Course.Builder().withId(courseId1).build()
        val course2 = Course.Builder().withId(courseId2).build()

        val student = Student.Builder().withEnrolledCourses(mutableListOf(course2)).build()

        assertEquals(student.enrolledCourses.size, 1)
        assertTrue(student.enrolledCourses.any { it.id == courseId2 })
        student.addEnrolledCourse(course2)
        assertEquals(student.enrolledCourses.size, 1)
        assertTrue(student.enrolledCourses.any { it.id == courseId2 })
        student.addEnrolledCourse(course1)
        assertEquals(student.enrolledCourses.size, 2)
        assertTrue(student.enrolledCourses.any { it.id == courseId2 })
        assertTrue(student.enrolledCourses.any { it.id == courseId1 })
    }

    @Test
    fun `test student delete a course in enrolled courses and return true if delete that element`() {
        val courseId1: Long = 1
        val courseId2: Long = 2
        val course1 = Course.Builder().withId(courseId1).build()
        val course2 = Course.Builder().withId(courseId2).build()

        val student = Student.Builder().withEnrolledCourses(mutableListOf(course2)).build()

        assertEquals(student.enrolledCourses.size, 1)
        assertTrue(student.enrolledCourses.any { it.id == courseId2 })

        assertFalse(student.deleteEnrolledCourse(course1))
        assertEquals(student.enrolledCourses.size, 1)
        assertTrue(student.enrolledCourses.any { it.id == courseId2 })

        assertTrue(student.deleteEnrolledCourse(course2))
        assertEquals(student.enrolledCourses.size, 0)
    }

    @Test
    fun `test student any coefficient is greater than some coefficient`() {
        val studiedDegree1: StudiedDegree = mockk { every { coefficient } returns 1f }
        val studiedDegree2: StudiedDegree = mockk { every { coefficient } returns 7f }
        val student0 = Student.Builder().withDegreeHistories(mutableListOf()).build()
        val student1 = Student.Builder().withDegreeHistories(mutableListOf(studiedDegree1, studiedDegree2)).build()
        val student2 = Student.Builder().withDegreeHistories(mutableListOf(studiedDegree1)).build()

        assertFalse(student0.anyCoefficientIsGreaterThan(0f))
        assertTrue(student1.anyCoefficientIsGreaterThan(5f))
        assertFalse(student2.anyCoefficientIsGreaterThan(5f))
    }

    @Test
    fun `test student max coefficient if empty is 0`() {
        val studiedDegree1: StudiedDegree = mockk { every { coefficient } returns 1f }
        val studiedDegree2: StudiedDegree = mockk { every { coefficient } returns 7f }
        val student0 = Student.Builder().withDegreeHistories(mutableListOf()).build()
        val student1 = Student.Builder().withDegreeHistories(mutableListOf(studiedDegree1, studiedDegree2)).build()
        val student2 = Student.Builder().withDegreeHistories(mutableListOf(studiedDegree1)).build()

        assertEquals(student0.maxCoefficient(), 0f)
        assertEquals(student1.maxCoefficient(), 7f)
        assertEquals(student2.maxCoefficient(), 1f)
    }

    @Test
    fun `test student get studied degree coefficient by degree if not exist is 0`() {
        val degreeAId: Long = 1
        val degreeA = Degree.Builder().withId(degreeAId).build()
        val degreeB = Degree.Builder().withId(100).build()

        val studiedDegree1: StudiedDegree = mockk {
            every { degree } returns degreeA
            every { coefficient } returns 1f
        }
        val studiedDegree2: StudiedDegree = mockk {
            every { degree } returns Degree.Builder().withId(2).build()
            every { coefficient } returns 7f
        }
        val student0 = Student.Builder().withDegreeHistories(mutableListOf()).build()
        val student1 = Student.Builder().withDegreeHistories(mutableListOf(studiedDegree1, studiedDegree2)).build()

        assertEquals(student0.getStudiedDegreeCoefficient(degreeA), 0f)
        assertEquals(student1.getStudiedDegreeCoefficient(degreeA), 1f)
        assertEquals(student1.getStudiedDegreeCoefficient(degreeB), 0f)
    }

    @Test
    fun `test student get passed subjects`() {
        val subjectA = Subject.Builder().build()
        val subjectB = Subject.Builder().build()
        val subjectC = Subject.Builder().build()

        val studiedSubject1: StudiedSubject = mockk {
            every { subject } returns subjectA
            every { passed() } returns true
        }
        val studiedSubject2: StudiedSubject = mockk {
            every { subject } returns subjectB
            every { passed() } returns true
        }
        val studiedSubject3: StudiedSubject = mockk {
            every { subject } returns subjectC
            every { passed() } returns false
        }
        val studiedDegree1: StudiedDegree =
            mockk { every { studied_subjects } returns listOf(studiedSubject1, studiedSubject3) }
        val studiedDegree2: StudiedDegree = mockk { every { studied_subjects } returns listOf(studiedSubject2) }
        val studiedDegree3: StudiedDegree =
            mockk { every { studied_subjects } returns listOf(studiedSubject3, studiedSubject2) }
        val student1 = Student.Builder().withDegreeHistories(mutableListOf(studiedDegree1, studiedDegree2)).build()
        val student2 = Student.Builder().withDegreeHistories(mutableListOf(studiedDegree3)).build()

        assertEquals(student1.getPassedSubjects().size, 2)
        assertTrue(student1.getPassedSubjects().any { it == subjectA })
        assertTrue(student1.getPassedSubjects().any { it == subjectB })

        assertEquals(student2.getPassedSubjects().size, 1)
        assertTrue(student2.getPassedSubjects().any { it == subjectB })
    }

    @Test
    fun `test student add degree in enrolled degrees only if it not exist in that list`() {
        val id1: Long = 1
        val id2: Long = 2
        val degree1 = Degree.Builder().withId(id1).build()
        val degree2 = Degree.Builder().withId(id2).build()

        val student = Student.Builder().withEnrolledDegrees(mutableListOf(degree2)).build()

        assertEquals(student.enrolledDegrees.size, 1)
        assertTrue(student.enrolledDegrees.any { it.id == id2 })
        student.addEnrolledDegree(degree2)
        assertEquals(student.enrolledDegrees.size, 1)
        assertTrue(student.enrolledDegrees.any { it.id == id2 })
        student.addEnrolledDegree(degree1)
        assertEquals(student.enrolledDegrees.size, 2)
        assertTrue(student.enrolledDegrees.any { it.id == id2 })
        assertTrue(student.enrolledDegrees.any { it.id == id1 })
    }

    @Test
    fun `test student add or update studied degree`() {
        val id1: Long = 1
        val id2: Long = 2
        val degree1 = Degree.Builder().withId(id1).build()
        val degree2 = Degree.Builder().withId(id2).build()
        val isRegular = true
        val plan = "2023"
        val quality = QualityState.ACTIVE
        val registryState = RegistryState.ACCEPTED
        val location = "Chilecito"
        val studiedDegree1 = StudiedDegree.Builder().
            withDegree(degree1).withIsRegular(isRegular).
            withPlan(plan).withQuality(quality).withRegistryState(registryState).
            withLocation(location).build()
        val studiedDegree2 = StudiedDegree.Builder().withDegree(degree2).build()
        val student = Student.Builder().withDegreeHistories(mutableListOf(studiedDegree1)).build()

        assertEquals(student.degreeHistories.size, 1)
        assertTrue(student.degreeHistories.any { it.degree == degree1 })
        student.addOrUpdateStudiedDegree(studiedDegree1)
        assertEquals(student.degreeHistories.size, 1)
        assertTrue(student.degreeHistories.any {
            it.degree == degree1 && it.isRegular && it.plan == plan && it.quality == quality
                    && it.registryState == registryState && it.location == location
        })

        student.addOrUpdateStudiedDegree(studiedDegree2)
        assertEquals(student.degreeHistories.size, 2)
        assertTrue(student.degreeHistories.any { it.degree == degree2 })
    }
}