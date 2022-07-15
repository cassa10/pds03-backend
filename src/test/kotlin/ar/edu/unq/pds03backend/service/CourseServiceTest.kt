package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.course.CourseRequestDTO
import ar.edu.unq.pds03backend.dto.course.CourseUpdateRequestDTO
import ar.edu.unq.pds03backend.dto.course.HourRequestDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.model.Course
import ar.edu.unq.pds03backend.model.Subject
import ar.edu.unq.pds03backend.repository.ICourseRepository
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.repository.ISubjectRepository
import ar.edu.unq.pds03backend.service.impl.CourseService
import ar.edu.unq.pds03backend.utils.SemesterHelper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek
import java.util.*

class CourseServiceTest {

    @RelaxedMockK
    private lateinit var semesterRepository: ISemesterRepository

    @RelaxedMockK
    private lateinit var subjectRepository: ISubjectRepository

    @RelaxedMockK
    private lateinit var courseRepository: ICourseRepository

    @RelaxedMockK
    private lateinit var quoteRequestRepository: IQuoteRequestRepository

    private lateinit var courseService: ICourseService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        courseService = CourseService(
            semesterRepository,
            subjectRepository,
            courseRepository,
            quoteRequestRepository
        )
    }

    @Test(expected = CourseNotFoundException::class)
    fun `test get course by id throw CourseNotFoundException if not found`() {
        val idCourse: Long = 1
        every { courseRepository.findById(idCourse) } returns Optional.empty()
        courseService.getById(idCourse)
    }

    @Test
    fun `test get course by id return course with same id`() {
        val idCourse: Long = 1
        val subject = Subject.Builder().withId(1).build()
        val course = Course.Builder().withId(idCourse).withSubject(subject).build()
        every { courseRepository.findById(idCourse) } returns Optional.of(course)
        assertEquals(courseService.getById(idCourse).id, course.id)
    }

    @Test
    fun `test get all courses by semester id and subject id`() {
        val idSemester: Long = 3
        val idSubject: Long = 10
        val subject = Subject.Builder().withId(idSubject).build()
        val semester = SemesterHelper.defaultSemester()
        semester.id = idSemester
        every { semesterRepository.findById(idSemester) } returns Optional.of(semester)
        every { subjectRepository.findById(idSubject) } returns Optional.of(subject)

        val idCourse1: Long = 1
        val idCourse2: Long = 2
        val course1 = Course.Builder().withId(idCourse1).withSubject(subject).withSemester(semester).build()
        val course2 = Course.Builder().withId(idCourse2).withSubject(subject).withSemester(semester).build()


        val listCoursesMatched = listOf(course1, course2)
        every { courseRepository.findAllBySemesterIdAndSubjectId(idSemester, idSubject) } returns listCoursesMatched

        val list = courseService.getAllBySemesterAndSubject(idSemester, idSubject)
        assertEquals(list.size, 2)
        assertTrue(list.any { it.id == idCourse1 })
        assertTrue(list.any { it.id == idCourse2 })
    }

    @Test(expected = SubjectNotFoundException::class)
    fun `test get all courses by semester id and subject id throws SubjectNotFoundException because subject not exists`() {
        val idSemester: Long = 3
        val idSubject: Long = 10
        val semester = SemesterHelper.defaultSemester()
        semester.id = idSemester
        every { semesterRepository.findById(idSemester) } returns Optional.of(semester)
        every { subjectRepository.findById(idSubject) } returns Optional.empty()
        courseService.getAllBySemesterAndSubject(idSemester, idSubject)
    }

    @Test(expected = SemesterNotFoundException::class)
    fun `test get all courses by semester id and subject id throws SemesterNotFoundException because semester not exists`() {
        val idSemester: Long = 3
        val idSubject: Long = 10
        val semester = SemesterHelper.defaultSemester()
        semester.id = idSemester
        every { semesterRepository.findById(idSemester) } returns Optional.empty()
        courseService.getAllBySemesterAndSubject(idSemester, idSubject)
    }

    @Test(expected = CourseAlreadyExist::class)
    fun `test create course with idSemester, idSubject, courseRequestDTO throws CourseAlreadyExist if external, subject and semester id already exists`() {
        val idSemester: Long = 3
        val idSubject: Long = 10
        val externalId = "A1"
        val subject = Subject.Builder().withId(idSubject).build()
        val semester = SemesterHelper.defaultSemester()
        semester.id = idSemester
        every { semesterRepository.findById(idSemester) } returns Optional.of(semester)
        every { subjectRepository.findById(idSubject) } returns Optional.of(subject)
        every {
            courseRepository.findByExternalIdAndSemesterIdAndSubjectId(
                externalId,
                idSemester,
                idSubject
            )
        } returns Optional.of(Course.Builder().build())

        val courseRequestDTO = defaultCourseRequestDTOWithExternalId(externalId)
        courseService.create(idSemester, idSubject, courseRequestDTO)
    }

    @Test
    fun `test create course with idSemester, idSubject, courseRequestDTO saves without error`() {
        val idSemester: Long = 3
        val idSubject: Long = 10
        val externalId = "A1"
        val subject = Subject.Builder().withId(idSubject).build()
        val semester = SemesterHelper.defaultSemester()
        semester.id = idSemester
        every { semesterRepository.findById(idSemester) } returns Optional.of(semester)
        every { subjectRepository.findById(idSubject) } returns Optional.of(subject)
        every {
            courseRepository.findByExternalIdAndSemesterIdAndSubjectId(externalId, idSemester, idSubject)
        } returns Optional.empty()
        every { courseRepository.save(any()) } returns Course.Builder().build()
        val courseRequestDTO = defaultCourseRequestDTOWithExternalId(externalId)
        courseService.create(idSemester, idSubject, courseRequestDTO)

        verify(exactly = 1) { courseRepository.save(any()) }
    }

    @Test
    fun `test update course with id and courseUpdateRequestDTO update without error`() {
        val courseId:Long = 1
        val course = Course.Builder().withId(1).build()
        every { courseRepository.findById(courseId) } returns Optional.of(course)
        every { courseRepository.save(any()) } returns course
        val courseUpdateRequestDTO = CourseUpdateRequestDTO(name = "name", listOf(), 10, listOf())
        courseService.update(courseId, courseUpdateRequestDTO)
        verify(exactly = 1) { courseRepository.save(any()) }
    }

    @Test
    fun `test update course with id and courseUpdateRequestDTO with hours update without error`() {
        val courseId:Long = 1
        val course = Course.Builder().withId(1).build()
        every { courseRepository.findById(courseId) } returns Optional.of(course)
        every { courseRepository.save(any()) } returns course
        val hoursReq:List<HourRequestDTO> = listOf(HourRequestDTO(from = "10:00", to = "11:00", day = DayOfWeek.MONDAY))
        val courseUpdateRequestDTO = CourseUpdateRequestDTO(name = "name", listOf(), 10, hoursReq)
        courseService.update(courseId, courseUpdateRequestDTO)
        verify(exactly = 1) { courseRepository.save(any()) }
    }

    @Test(expected = CannotUpdateCourseInvalidTotalQuotesException::class)
    fun `test update course with id and courseUpdateRequestDTO update with CannotUpdateCourseInvalidTotalQuotesException error`() {
        val courseId:Long = 1
        val currentTotalQuotes = 10
        val course = Course.Builder().withId(1).withTotalQuotes(currentTotalQuotes).build()
        every { courseRepository.findById(courseId) } returns Optional.of(course)
        every { courseRepository.save(any()) } returns course
        val updateTotalQuotes = 5
        val courseUpdateRequestDTO = CourseUpdateRequestDTO(name = "name", listOf(), updateTotalQuotes, listOf())
        courseService.update(courseId, courseUpdateRequestDTO)
    }

    @Test(expected = InvalidRequestHours::class)
    fun `test update course with id and courseUpdateRequestDTO update with InvalidRequestHours error`() {
        val courseId:Long = 1
        val course = Course.Builder().withId(1).build()
        every { courseRepository.findById(courseId) } returns Optional.of(course)
        every { courseRepository.save(any()) } returns course
        val hoursReq: List<HourRequestDTO> = listOf(HourRequestDTO(from = "12:00", to = "11:00", day = DayOfWeek.MONDAY))
        val courseUpdateRequestDTO = CourseUpdateRequestDTO(name = "name", listOf(), 10, hoursReq)
        courseService.update(courseId, courseUpdateRequestDTO)
    }

    @Test
    fun `test delete course with id without error`() {
        val courseId:Long = 1
        val course = Course.Builder().withId(1).build()
        every { courseRepository.findById(courseId) } returns Optional.of(course)
        every { courseRepository.deleteById(any()) } returns Unit
        every { quoteRequestRepository.countByCourseId(courseId) } returns 0
        courseService.delete(courseId)
        verify(exactly = 1) { courseRepository.deleteById(courseId) }
    }

    @Test(expected = CannotDeleteCourseException::class)
    fun `test delete course with id with CannotDeleteCourseException error`() {
        val courseId:Long = 1
        val course = Course.Builder().withId(1).build()
        every { courseRepository.findById(courseId) } returns Optional.of(course)
        every { courseRepository.deleteById(any()) } returns Unit
        every { quoteRequestRepository.countByCourseId(courseId) } returns 1
        courseService.delete(courseId)
    }

    private fun defaultCourseRequestDTOWithExternalId(externalId: String) = CourseRequestDTO(
        externalId = externalId,
        name = "",
        assignedTeachers = listOf(),
        totalQuotes = 0,
        hours = listOf(),
        location = "",
    )
}