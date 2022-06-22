package ar.edu.unq.pds03backend

import ar.edu.unq.pds03backend.exception.UserNotFoundException
import ar.edu.unq.pds03backend.model.*
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.repository.IUserRepository
import ar.edu.unq.pds03backend.service.IUserService
import ar.edu.unq.pds03backend.service.impl.UserService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

private const val USER_ID: Long = 1
private const val USER_NAME = "user name"
private const val USER_FIRST_NAME = "user first name"
private const val USER_LAST_NAME = "user last name"
private const val USER_DNI = "user dni"
private const val USER_LEGAJO = "user legajo"
private const val USER_MAX_COEFFICIENT = 10f
private const val DEGREE_ID: Long = 1
private const val DEGREE_NAME = "degree name"
private const val DEGREE_ACRONYM = "degree acronym"
private const val COEFFICIENT = 7f
private const val SUBJECT_ID: Long = 1
private const val SUBJECT_NAME = "subject name"
private const val COURSE_ID: Long = 1
private const val COURSE_NAME = "course name"
private const val COURSE_ASSIGNED_TEACHERS = "course assigned teachers"
private const val QUOTE_REQUEST_ID: Long = 1
private const val QUOTE_REQUEST_COMMENT = "quote request comment"
private const val QUOTE_REQUEST_ADMIN_COMMENT = "quote request admin comment"
private const val USER_EMAIL = "user email"

class UserServiceTest {

    @RelaxedMockK
    private lateinit var userRepository: IUserRepository

    @RelaxedMockK
    private lateinit var quoteRequestRepository: IQuoteRequestRepository

    @RelaxedMockK
    private lateinit var semesterRepository: ISemesterRepository

    private lateinit var userService: IUserService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        userService = UserService(userRepository, quoteRequestRepository, semesterRepository)
    }

    @Test(expected = UserNotFoundException::class)
    fun `given a user not found when call getById then it should throw UserNotFoundException`() {
        val optionalUserMock = mockk<Optional<User>> {
            every { isPresent } returns false
        }
        every { userRepository.findById(any()) } returns optionalUserMock

        userService.getById(USER_ID)
    }

    @Test
    fun `given a student when call getById then it should UserResponseDTO`() {
        val quoteRequestMock = mockk<QuoteRequest> {
            every { course.subject.id } returns SUBJECT_ID
            every { course.subject.name } returns SUBJECT_NAME
            every { course.id } returns COURSE_ID
            every { course.name } returns COURSE_NAME
            every { course.assigned_teachers } returns COURSE_ASSIGNED_TEACHERS
            every { id } returns QUOTE_REQUEST_ID
            every { state } returns QuoteState.APPROVED
            every { comment } returns QUOTE_REQUEST_COMMENT
            every { adminComment } returns QUOTE_REQUEST_ADMIN_COMMENT
        }
        val enrolledCourseMock = mockk<Course> {
            every { subject.id } returns SUBJECT_ID
            every { subject.name } returns SUBJECT_NAME
            every { id } returns COURSE_ID
            every { name } returns COURSE_NAME
            every { assigned_teachers } returns COURSE_ASSIGNED_TEACHERS
        }
        val enrolledDegreeMock = mockk<Degree> {
            every { id } returns DEGREE_ID
            every { name } returns DEGREE_NAME
            every { acronym } returns DEGREE_ACRONYM
        }
        val userMock = mockk<Student> {
            every { id } returns USER_ID
            every { username } returns USER_NAME
            every { firstName } returns USER_FIRST_NAME
            every { lastName } returns USER_LAST_NAME
            every { dni } returns USER_DNI
            every { email } returns USER_EMAIL
            every { isStudent() } returns true
            every { legajo } returns USER_LEGAJO
            every { maxCoefficient() } returns USER_MAX_COEFFICIENT
            every { enrolledDegrees } returns listOf(enrolledDegreeMock)
            every { getStudiedDegreeCoefficient(any()) } returns COEFFICIENT
            every { enrolledCourses } returns mutableListOf(enrolledCourseMock)
        }
        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(mockk(relaxed = true))
        every { userRepository.findById(any()) } returns Optional.of(userMock)
        every { quoteRequestRepository.findAllByStudentIdAndCourseSemesterIdAndInStates(any(), any(), any(), any()) } returns listOf(quoteRequestMock)

        val actual = userService.getById(USER_ID)

        assertEquals(USER_ID, actual.id)
        assertTrue(actual.isStudent)
        assertEquals(USER_NAME, actual.username)
        assertEquals(USER_FIRST_NAME, actual.firstName)
        assertEquals(USER_LAST_NAME, actual.lastName)
        assertEquals(USER_EMAIL, actual.email)
        assertEquals(USER_DNI, actual.dni)
        assertEquals(USER_LEGAJO, actual.legajo)
        assertEquals(USER_MAX_COEFFICIENT, actual.maxCoefficient)
        with(actual.enrolledDegrees[0]) {
            assertEquals(DEGREE_ID, id)
            assertEquals(DEGREE_NAME, name)
            assertEquals(DEGREE_ACRONYM, acronym)
            assertEquals(COEFFICIENT, coefficient)
        }
        with(actual.enrolledSubjects[0]) {
            assertEquals(SUBJECT_ID, id)
            assertEquals(SUBJECT_NAME, name)
            with(course) {
                assertEquals(COURSE_ID, id)
                assertEquals(COURSE_NAME, name)
                assertEquals(COURSE_ASSIGNED_TEACHERS, assigned_teachers)
            }
        }
        with(actual.requestedSubjects[0]) {
            assertEquals(SUBJECT_ID, id)
            assertEquals(SUBJECT_NAME, name)
            assertEquals(QUOTE_REQUEST_ID, idQuoteRequest)
            assertEquals(QuoteState.APPROVED, status)
            assertEquals(QUOTE_REQUEST_COMMENT, comment)
            assertEquals(QUOTE_REQUEST_ADMIN_COMMENT, adminComment)
        }
    }

    @Test
    fun `given a director when call getById then it should UserResponseDTO`() {
        val userMock = mockk<Student> {
            every { id } returns USER_ID
            every { username } returns USER_NAME
            every { firstName } returns USER_FIRST_NAME
            every { lastName } returns USER_LAST_NAME
            every { dni } returns USER_DNI
            every { email } returns USER_EMAIL
            every { isStudent() } returns false
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(mockk())
        every { userRepository.findById(any()) } returns Optional.of(userMock)

        val actual = userService.getById(USER_ID)

        assertEquals(USER_ID, actual.id)
        assertFalse(actual.isStudent)
        assertEquals(USER_NAME, actual.username)
        assertEquals(USER_FIRST_NAME, actual.firstName)
        assertEquals(USER_LAST_NAME, actual.lastName)
        assertEquals(USER_EMAIL, actual.email)
        assertEquals(USER_DNI, actual.dni)
        assertEquals("", actual.legajo)
        assertEquals(0f, actual.maxCoefficient)
        assertTrue(actual.enrolledDegrees.isEmpty())
        assertTrue(actual.enrolledSubjects.isEmpty())
        assertTrue(actual.requestedSubjects.isEmpty())
    }
}
