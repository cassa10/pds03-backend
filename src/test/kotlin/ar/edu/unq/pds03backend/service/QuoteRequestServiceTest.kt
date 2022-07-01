package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.model.*
import ar.edu.unq.pds03backend.repository.*
import ar.edu.unq.pds03backend.service.IQuoteRequestService
import ar.edu.unq.pds03backend.service.impl.QuoteRequestService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.*

private const val QUOTE_REQUEST_ID: Long = 1
private const val STUDENT_ID: Long = 1
private const val COMMENT = "No puedo cursar en otro horario"
private const val COURSE_ID: Long = 1
private const val COURSE_NAME = "Course name"
private const val SUBJECT_ID: Long = 1
private const val SUBJECT_NAME = "Subject name"
private const val COURSE_ASSIGNED_TEACHERS = "Course assigned teachers"
private const val SEMESTER_ID: Long = 1
private const val SEMESTER_NAME = "Semester name"
private const val HOUR_FROM = "Hour from"
private const val HOUR_TO = "Hour to"
private const val STUDENT_FIRST_NAME = "Student first name"
private const val STUDENT_LAST_NAME = "Student last name"
private const val STUDENT_DNI = "Student dni"
private const val STUDENT_EMAIL = "Student email"
private const val STUDENT_LEGAJO = "Student legajo"
private const val DEGREE_ID: Long = 1
private const val DEGREE_NAME = "Degree name"
private const val DEGREE_ACRONYM = "Degree acronym"
private const val STUDENT_COEFFICIENT = 1f
private const val HOUR_MESSAGE = "Hour message"

class QuoteRequestServiceTest {

    @RelaxedMockK
    private lateinit var quoteRequestRepository: IQuoteRequestRepository

    @RelaxedMockK
    private lateinit var courseRepository: ICourseRepository

    @RelaxedMockK
    private lateinit var studentRepository: IStudentRepository

    @RelaxedMockK
    private lateinit var semesterRepository: ISemesterRepository

    @RelaxedMockK
    private lateinit var configurableValidationRepository: IConfigurableValidationRepository

    private lateinit var quoteRequestService: IQuoteRequestService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        quoteRequestService = QuoteRequestService(
                quoteRequestRepository,
                courseRepository,
                studentRepository,
                semesterRepository,
                configurableValidationRepository
        )
    }

    @Test(expected = SemesterNotFoundException::class)
    fun `given a semester not found when create quote request then it should throw SemesterNotFoundException`() {
        val optionalSemesterMock = mockk<Optional<Semester>> {
            every { isPresent } returns false
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns optionalSemesterMock

        quoteRequestService.create(mockk())
    }

    @Test(expected = CannotCreateQuoteRequestException::class)
    fun `given a quote request not available when create quote request then it should throw CannotCreateQuoteRequestException`() {
        val semesterMock = mockk<Semester> {
            every { isAcceptQuoteRequestsAvailable() } returns false
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)

        quoteRequestService.create(mockk())
    }

    @Test(expected = CourseNotFoundException::class)
    fun `given a quote request without courses when create quote request then it should throw CourseNotFoundException`() {
        val quoteRequestRequestDto = mockk<QuoteRequestRequestDTO> {
            every { idCourses } returns emptyList()
        }

        val semesterMock = mockk<Semester> {
            every { isAcceptQuoteRequestsAvailable() } returns true
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)
        every { courseRepository.findAllById(any()) } returns emptyList()

        quoteRequestService.create(quoteRequestRequestDto)
    }

    @Test(expected = StudentNotFoundException::class)
    fun `given a quote request without student id when create quote request then it should throw StudentNotFoundException`() {
        val courseMock = mockk<Course>()
        val quoteRequestRequestDto = mockk<QuoteRequestRequestDTO> {
            every { idCourses } returns listOf(1)
            every { idStudent } returns STUDENT_ID
        }

        val semesterMock = mockk<Semester> {
            every { isAcceptQuoteRequestsAvailable() } returns true
        }

        val optionalStudentMock = mockk<Optional<Student>> {
            every { isPresent } returns false
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)
        every { courseRepository.findAllById(any()) } returns listOf(courseMock)
        every { studentRepository.findById(STUDENT_ID) } returns optionalStudentMock

        quoteRequestService.create(quoteRequestRequestDto)
    }

    @Test(expected = StudentNotEnrolledInSomeDegree::class)
    fun `given a student not enrolled in some degree when create quote request then it should throw StudentNotEnrolledInSomeDegree`() {
        val courseMock = mockk<Course>(relaxed = true)
        val quoteRequestRequestDto = mockk<QuoteRequestRequestDTO> {
            every { idCourses } returns listOf(1)
            every { idStudent } returns STUDENT_ID
        }

        val semesterMock = mockk<Semester> {
            every { isAcceptQuoteRequestsAvailable() } returns true
        }

        val studentMock = mockk<Student> {
            every { isStudyingAnyDegree(any()) } returns false
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)
        every { courseRepository.findAllById(any()) } returns listOf(courseMock)
        every { studentRepository.findById(STUDENT_ID) } returns Optional.of(studentMock)

        quoteRequestService.create(quoteRequestRequestDto)
    }

    @Test(expected = StudentHasAlreadyEnrolledSubject::class)
    fun `given a student who has a subject enrolled when create quote request then it should throw StudentHasAlreadyEnrolledSubject`() {
        val courseMock = mockk<Course>(relaxed = true)
        val quoteRequestRequestDto = mockk<QuoteRequestRequestDTO> {
            every { idCourses } returns listOf(1)
            every { idStudent } returns STUDENT_ID
        }

        val semesterMock = mockk<Semester> {
            every { isAcceptQuoteRequestsAvailable() } returns true
        }

        val studentMock = mockk<Student>(relaxed = true) {
            every { isStudyingAnyDegree(any()) } returns true
            every { isStudyingOrEnrolled(any()) } returns true
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)
        every { courseRepository.findAllById(any()) } returns listOf(courseMock)
        every { studentRepository.findById(STUDENT_ID) } returns Optional.of(studentMock)

        quoteRequestService.create(quoteRequestRequestDto)
    }

    @Test(expected = PrerequisiteSubjectsValidationNotFoundException::class)
    fun `given a student who does not meet prerequisites when create quote request then it should throw PrerequisiteSubjectsValidationNotFoundException`() {
        val courseMock = mockk<Course>(relaxed = true)
        val quoteRequestRequestDto = mockk<QuoteRequestRequestDTO> {
            every { idCourses } returns listOf(1)
            every { idStudent } returns STUDENT_ID
        }

        val semesterMock = mockk<Semester> {
            every { isAcceptQuoteRequestsAvailable() } returns true
        }

        val studentMock = mockk<Student>(relaxed = true) {
            every { isStudyingAnyDegree(any()) } returns true
            every { isStudyingOrEnrolled(any()) } returns false
        }

        val optionalConfigValidationMock = mockk<Optional<ConfigurableValidation>> {
            every { isPresent.not() } returns true
            every { isPresent } returns false
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)
        every { courseRepository.findAllById(any()) } returns listOf(courseMock)
        every { studentRepository.findById(STUDENT_ID) } returns Optional.of(studentMock)
        every { configurableValidationRepository.findByValidation(Validation.PREREQUISITE_SUBJECTS) } returns optionalConfigValidationMock

        quoteRequestService.create(quoteRequestRequestDto)
    }

    @Test(expected = StudentNotApplyWithPrerequisiteSubjects::class)
    fun `given a student who does not meet prerequisites when create quote request then it should throw StudentNotApplyWithPrerequisiteSubjects`() {
        val courseMock = mockk<Course>(relaxed = true)
        val quoteRequestRequestDto = mockk<QuoteRequestRequestDTO> {
            every { idCourses } returns listOf(1)
            every { idStudent } returns STUDENT_ID
        }

        val semesterMock = mockk<Semester> {
            every { isAcceptQuoteRequestsAvailable() } returns true
        }

        val studentMock = mockk<Student>(relaxed = true) {
            every { isStudyingAnyDegree(any()) } returns true
            every { isStudyingOrEnrolled(any()) } returns false
        }

        val configValidation = mockk<ConfigurableValidation> {
            every { validate(any()) } returns true
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)
        every { courseRepository.findAllById(any()) } returns listOf(courseMock)
        every { studentRepository.findById(STUDENT_ID) } returns Optional.of(studentMock)
        every { configurableValidationRepository.findByValidation(Validation.PREREQUISITE_SUBJECTS) } returns Optional.of(configValidation)

        quoteRequestService.create(quoteRequestRequestDto)
    }

    @Test
    fun `given a none quote request when call getAll then it should return list empty`() {
        val actual = quoteRequestService.getAll(emptySet())

        assertTrue(actual.isEmpty())
        assertEquals(0, actual.size)
    }

    @Test
    fun `given a list quote request when call getAll then it should return list QuoteRequestResponseDTO`() {
        val hourMock = mockk<Hour> {
            every { day } returns DayOfWeek.FRIDAY
            every { getFromString() } returns HOUR_FROM
            every { getToString() } returns HOUR_TO
        }
        val semesterMock = mockk<Semester> {
            every { id } returns SEMESTER_ID
            every { isSndSemester } returns false
            every { year } returns 2022
            every { name() } returns SEMESTER_NAME
            every { acceptQuoteRequestsFrom } returns LocalDateTime.MAX
            every { acceptQuoteRequestsTo } returns LocalDateTime.MAX
            every { isCurrent() } returns true
            every { isAcceptQuoteRequestsAvailable() } returns true
        }
        val courseMock = mockk<Course> {
            every { id } returns COURSE_ID
            every { semester } returns semesterMock
            every { subject.id } returns SUBJECT_ID
            every { subject.name } returns SUBJECT_NAME
            every { name } returns COURSE_NAME
            every { assigned_teachers } returns COURSE_ASSIGNED_TEACHERS
            every { total_quotes } returns 30
            every { hours } returns mutableListOf(hourMock)
        }
        val degreeMock = mockk<Degree> {
            every { id } returns DEGREE_ID
            every { name } returns DEGREE_NAME
            every { acronym } returns DEGREE_ACRONYM
        }
        val studentMock = mockk<Student> {
            every { id } returns STUDENT_ID
            every { firstName } returns STUDENT_FIRST_NAME
            every { lastName } returns STUDENT_LAST_NAME
            every { dni } returns STUDENT_DNI
            every { email } returns STUDENT_EMAIL
            every { legajo } returns STUDENT_LEGAJO
            every { maxCoefficient() } returns 0f
            every { enrolledDegrees } returns mutableListOf(degreeMock)
            every { getStudiedDegreeCoefficient(any()) } returns STUDENT_COEFFICIENT
        }
        val quoteRequestMock = mockk<QuoteRequest> {
            every { id } returns QUOTE_REQUEST_ID
            every { course } returns courseMock
            every { student } returns studentMock
            every { state } returns QuoteState.PENDING
            every { comment } returns COMMENT
            every { createdOn } returns LocalDateTime.MAX
        }
        every { quoteRequestRepository.findAllByInStates(any(), any()) } returns listOf(quoteRequestMock)

        val actual = quoteRequestService.getAll(setOf(mockk()))

        with(actual[0]) {
            assertEquals(QUOTE_REQUEST_ID, id)
            assertEquals(QuoteState.PENDING, state)
            assertEquals(COMMENT, comment)
            assertEquals(LocalDateTime.MAX, createdOn)
            with(course) {
                assertEquals(COURSE_ID, id)
                assertEquals(SUBJECT_ID, subject.id)
                assertEquals(SUBJECT_NAME, subject.name)
                assertEquals(COURSE_NAME, name)
                assertEquals(COURSE_ASSIGNED_TEACHERS, assigned_teachers)
                assertEquals(30, totalQuotes)
                with(semester) {
                    assertEquals(SEMESTER_ID, id)
                    assertEquals(false, isSndSemester)
                    assertEquals(2022, year)
                    assertEquals(SEMESTER_NAME, name)
                    assertEquals(LocalDateTime.MAX, acceptQuoteRequestsFrom)
                    assertEquals(LocalDateTime.MAX, acceptQuoteRequestsTo)
                    assertEquals(true, isCurrentSemester)
                    assertEquals(true, isAcceptQuoteRequestsAvailable)
                }
                with(hours[0]) {
                    assertEquals(DayOfWeek.FRIDAY, day)
                    assertEquals(HOUR_FROM, from)
                    assertEquals(HOUR_TO, to)
                }
            }
            with(student) {
                assertEquals(STUDENT_ID, id)
                assertEquals(STUDENT_FIRST_NAME, firstName)
                assertEquals(STUDENT_LAST_NAME, lastName)
                assertEquals(STUDENT_DNI, dni)
                assertEquals(STUDENT_EMAIL, email)
                assertEquals(STUDENT_LEGAJO, legajo)
                assertEquals(0f, maxCoefficient)
                with(enrolledDegrees[0]) {
                    assertEquals(DEGREE_ID, id)
                    assertEquals(DEGREE_NAME, name)
                    assertEquals(DEGREE_ACRONYM, acronym)
                    assertEquals(STUDENT_COEFFICIENT, coefficient)
                }
            }
        }
    }

    @Test(expected = CourseNotFoundException::class)
    fun `given a course id when call get all by course and student but course id not found then it should throw CourseNotFoundException`() {
        val optionalCourseMock = mockk<Optional<Course>> {
            every { isPresent } returns false
        }
        every { courseRepository.findById(any()) } returns optionalCourseMock

        quoteRequestService.getAllByCourseAndStudent(COURSE_ID, STUDENT_ID, emptySet())
    }

    @Test(expected = StudentNotFoundException::class)
    fun `given a student id when call get all by course and student but student id not found then it should throw StudentNotFoundException`() {
        val optionalStudentMock = mockk<Optional<Student>> {
            every { isPresent } returns false
        }
        every { courseRepository.findById(any()) } returns Optional.of(mockk())
        every { studentRepository.findById(any()) } returns optionalStudentMock

        quoteRequestService.getAllByCourseAndStudent(COURSE_ID, STUDENT_ID, emptySet())
    }

    @Test
    fun `given a student id and course id when call get all by course and student then it should return list empty`() {
        val courseMock = mockk<Course> {
            every { id } returns COURSE_ID
        }
        val studentMock = mockk<Student> {
            every { id } returns STUDENT_ID
        }
        every { courseRepository.findById(any()) } returns Optional.of(courseMock)
        every { studentRepository.findById(any()) } returns Optional.of(studentMock)
        every { quoteRequestRepository.findAllByCourseIdAndStudentIdAndInStates(any(), any(), any(), any()) } returns emptyList()

        val actual = quoteRequestService.getAllByCourseAndStudent(COURSE_ID, STUDENT_ID, emptySet())

        assertTrue(actual.isEmpty())
        assertEquals(0, actual.size)
    }

    @Test(expected = CourseNotFoundException::class)
    fun `given a id course when call get all by course but course id not found then it should CourseNotFound`() {
        val optionalCourseMock = mockk<Optional<Course>> {
            every { isPresent } returns false
        }
        every { courseRepository.findById(any()) } returns optionalCourseMock

        quoteRequestService.getAllByCourse(COURSE_ID, emptySet())
    }

    @Test
    fun `given course id when call get all by course then it should return list empty`() {
        val courseMock = mockk<Course> {
            every { id } returns COURSE_ID
        }
        every { courseRepository.findById(any()) } returns Optional.of(courseMock)
        every { quoteRequestRepository.findAllByCourseIdAndInStates(any(), any(), any()) } returns emptyList()

        val actual = quoteRequestService.getAllByCourse(COURSE_ID, emptySet())

        assertTrue(actual.isEmpty())
        assertEquals(0, actual.size)
    }

    @Test
    fun `given a student id when call get all current semester by student then it should return list empty`() {
        val studentMock = mockk<Student> {
            every { id } returns STUDENT_ID
        }
        val semesterMock = mockk<Semester> {
            every { id } returns SEMESTER_ID
        }
        every { studentRepository.findById(any()) } returns Optional.of(studentMock)
        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)
        every { quoteRequestRepository.findAllByStudentIdAndCourseSemesterIdAndInStates(any(), any(), any(), any()) } returns emptyList()

        val actual = quoteRequestService.getAllCurrentSemesterByStudent(STUDENT_ID, emptySet())

        assertTrue(actual.isEmpty())
        assertEquals(0, actual.size)
    }

    @Test
    fun `given a quote request id when call get by id then it should return QuoteRequestWithWarningsResponseDTO with all warnings`() {
        val hourMock = mockk<Hour> {
            every { day } returns DayOfWeek.FRIDAY
            every { getFromString() } returns HOUR_FROM
            every { getToString() } returns HOUR_TO
            every { anyIntercept(any()) } returns true
            every { String() } returns HOUR_MESSAGE
        }
        val semesterMock = mockk<Semester> {
            every { id } returns SEMESTER_ID
            every { isSndSemester } returns false
            every { year } returns 2022
            every { name() } returns SEMESTER_NAME
            every { acceptQuoteRequestsFrom } returns LocalDateTime.MAX
            every { acceptQuoteRequestsTo } returns LocalDateTime.MAX
            every { isCurrent() } returns true
            every { isAcceptQuoteRequestsAvailable() } returns true
        }
        val courseMock = mockk<Course> {
            every { id } returns COURSE_ID
            every { semester } returns semesterMock
            every { subject.id } returns SUBJECT_ID
            every { subject.name } returns SUBJECT_NAME
            every { name } returns COURSE_NAME
            every { assigned_teachers } returns COURSE_ASSIGNED_TEACHERS
            every { total_quotes } returns 30
            every { hours } returns mutableListOf(hourMock)
        }
        val degreeMock = mockk<Degree> {
            every { id } returns DEGREE_ID
            every { name } returns DEGREE_NAME
            every { acronym } returns DEGREE_ACRONYM
        }
        val studentMock = mockk<Student>(relaxed = true) {
            every { id } returns STUDENT_ID
            every { firstName } returns STUDENT_FIRST_NAME
            every { lastName } returns STUDENT_LAST_NAME
            every { dni } returns STUDENT_DNI
            every { email } returns STUDENT_EMAIL
            every { legajo } returns STUDENT_LEGAJO
            every { maxCoefficient() } returns 0f
            every { enrolledDegrees } returns mutableListOf(degreeMock)
            every { getStudiedDegreeCoefficient(any()) } returns STUDENT_COEFFICIENT
            every { enrolledCourses } returns mutableListOf(mockk(relaxed = true))
        }
        val quoteRequestMock = mockk<QuoteRequest> {
            every { id } returns QUOTE_REQUEST_ID
            every { course } returns courseMock
            every { student } returns studentMock
            every { state } returns QuoteState.PENDING
            every { comment } returns COMMENT
            every { createdOn } returns LocalDateTime.MAX
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)
        every { quoteRequestRepository.findAllByInStatesAndSkipIdAndStudentIdAndCourseSemesterId(any(), any(), any(), any(), any()) } returns listOf(quoteRequestMock)
        every { quoteRequestRepository.findById(any()) } returns Optional.of(quoteRequestMock)

        val actual = quoteRequestService.getById(QUOTE_REQUEST_ID)

        with(actual) {
            assertEquals(QUOTE_REQUEST_ID, id)
            assertEquals(QuoteState.PENDING, state)
            assertEquals(COMMENT, comment)
            assertEquals(LocalDateTime.MAX, createdOn)
            assertEquals(WarningType.CRITICAL, warnings[0].type)
            assertEquals(WarningType.MEDIUM, warnings[1].type)
            assertEquals(WarningType.LOW, warnings[2].type)
        }
    }

    @Test
    fun `given a quote request id when call get by id then it should return QuoteRequestWithWarningsResponseDTO without warnings`() {
        val hourMock = mockk<Hour> {
            every { day } returns DayOfWeek.FRIDAY
            every { getFromString() } returns HOUR_FROM
            every { getToString() } returns HOUR_TO
            every { anyIntercept(any()) } returns false
        }
        val semesterMock = mockk<Semester> {
            every { id } returns SEMESTER_ID
            every { isSndSemester } returns false
            every { year } returns 2022
            every { name() } returns SEMESTER_NAME
            every { acceptQuoteRequestsFrom } returns LocalDateTime.MAX
            every { acceptQuoteRequestsTo } returns LocalDateTime.MAX
            every { isCurrent() } returns true
            every { isAcceptQuoteRequestsAvailable() } returns true
        }
        val courseMock = mockk<Course> {
            every { id } returns COURSE_ID
            every { semester } returns semesterMock
            every { subject.id } returns SUBJECT_ID
            every { subject.name } returns SUBJECT_NAME
            every { name } returns COURSE_NAME
            every { assigned_teachers } returns COURSE_ASSIGNED_TEACHERS
            every { total_quotes } returns 30
            every { hours } returns mutableListOf(hourMock)
        }
        val degreeMock = mockk<Degree> {
            every { id } returns DEGREE_ID
            every { name } returns DEGREE_NAME
            every { acronym } returns DEGREE_ACRONYM
        }
        val studentMock = mockk<Student>(relaxed = true) {
            every { id } returns STUDENT_ID
            every { firstName } returns STUDENT_FIRST_NAME
            every { lastName } returns STUDENT_LAST_NAME
            every { dni } returns STUDENT_DNI
            every { email } returns STUDENT_EMAIL
            every { legajo } returns STUDENT_LEGAJO
            every { maxCoefficient() } returns 0f
            every { enrolledDegrees } returns mutableListOf(degreeMock)
            every { getStudiedDegreeCoefficient(any()) } returns STUDENT_COEFFICIENT
            every { enrolledCourses } returns mutableListOf(mockk(relaxed = true))
            every { passedAllPrerequisiteSubjects(any()) } returns true
            every { anyCoefficientIsGreaterThan(any()) } returns true
        }
        val quoteRequestMock = mockk<QuoteRequest> {
            every { id } returns QUOTE_REQUEST_ID
            every { course } returns courseMock
            every { student } returns studentMock
            every { state } returns QuoteState.PENDING
            every { comment } returns COMMENT
            every { createdOn } returns LocalDateTime.MAX
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)
        every { quoteRequestRepository.findAllByInStatesAndSkipIdAndStudentIdAndCourseSemesterId(any(), any(), any(), any(), any()) } returns listOf(quoteRequestMock)
        every { quoteRequestRepository.findById(any()) } returns Optional.of(quoteRequestMock)

        val actual = quoteRequestService.getById(QUOTE_REQUEST_ID)

        with(actual) {
            assertTrue(warnings.isEmpty())
            assertEquals(QUOTE_REQUEST_ID, id)
            assertEquals(QuoteState.PENDING, state)
            assertEquals(COMMENT, comment)
            assertEquals(LocalDateTime.MAX, createdOn)
            assertEquals(0, warnings.size)
        }
    }
}
