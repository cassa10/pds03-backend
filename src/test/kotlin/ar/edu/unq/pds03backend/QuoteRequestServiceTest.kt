package ar.edu.unq.pds03backend

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
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.util.*

private const val QUOTE_REQUEST_ID: Long = 1
private const val STUDENT_ID: Long = 1
private const val COMMENT = "No puedo cursar en otro horario"

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
            every { isEmpty } returns true
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
    fun `given a student applying for a subject when create quote request then it should save quote request`() {
        val courseMock = mockk<Course>(relaxed = true)
        val quoteRequestRequestDto = mockk<QuoteRequestRequestDTO> {
            every { idCourses } returns listOf(1)
            every { idStudent } returns STUDENT_ID
            every { comment } returns COMMENT
        }

        val semesterMock = mockk<Semester> {
            every { isAcceptQuoteRequestsAvailable() } returns true
        }

        val studentMock = mockk<Student>(relaxed = true) {
            every { isStudyingAnyDegree(any()) } returns true
            every { isStudyingOrEnrolled(any()) } returns false
        }

        val configValidation = mockk<ConfigurableValidation> {
            every { validate(any()) } returns false
        }

        val optionalQuoteRequestMock = mockk<Optional<QuoteRequest>> {
            every { isPresent } returns false
        }

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)
        every { courseRepository.findAllById(any()) } returns listOf(courseMock)
        every { studentRepository.findById(STUDENT_ID) } returns Optional.of(studentMock)
        every { configurableValidationRepository.findByValidation(Validation.PREREQUISITE_SUBJECTS) } returns Optional.of(configValidation)
        every { quoteRequestRepository.findByCourseIdAndStudentId(any(), any()) } returns optionalQuoteRequestMock

        quoteRequestService.create(quoteRequestRequestDto)

        //TODO: adds asserts
    }
}
