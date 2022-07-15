package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.semester.SemesterRequestDTO
import ar.edu.unq.pds03backend.dto.semester.UpdateSemesterRequestDTO
import ar.edu.unq.pds03backend.exception.InvalidRequestAcceptRequestQuotesDates
import ar.edu.unq.pds03backend.exception.SemesterAlreadyExistException
import ar.edu.unq.pds03backend.exception.SemesterNotFoundException
import ar.edu.unq.pds03backend.model.Semester
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.service.impl.SemesterService
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime
import java.util.*

class SemesterServiceTest {

    companion object {
        const val SEMESTER_ID: Long = 1
        const val SEMESTER_NAME = "semester name"
    }

    @RelaxedMockK
    private lateinit var semesterRepository: ISemesterRepository

    private lateinit var semesterService: SemesterService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        semesterService = SemesterService(semesterRepository)
    }

    @Test(expected = SemesterNotFoundException::class)
    fun `given a semester id not found then call get semester by id then it should throw SemesterNotFoundException`() {
        val optionalSemesterMock = Optional.empty<Semester>()

        every { semesterRepository.findById(any()) } returns optionalSemesterMock

        semesterService.getSemesterById(SEMESTER_ID)
    }

    @Test
    fun `given a semester id then call get semester by id then it should throw SemesterResponseDTO`() {
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

        every { semesterRepository.findById(any()) } returns Optional.of(semesterMock)

        val actual = semesterService.getSemesterById(SEMESTER_ID)

        with(actual) {
            assertEquals(SEMESTER_ID, id)
            assertFalse(isSndSemester)
            assertEquals(2022, year)
            assertEquals(SEMESTER_NAME, name)
            assertEquals(LocalDateTime.MAX, acceptQuoteRequestsFrom)
            assertEquals(LocalDateTime.MAX, acceptQuoteRequestsTo)
            assertTrue(isCurrentSemester)
            assertTrue(isAcceptQuoteRequestsAvailable)
        }
    }

    @Test(expected = SemesterNotFoundException::class)
    fun `given a year and isSndSemester not found when call get semester by year and isSndSemester then it should throw SemesterNotFoundException`() {
        val optionalSemesterMock = Optional.empty<Semester>()

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns optionalSemesterMock

        semesterService.getSemesterByYearAndIsSndSemester(2022, false)
    }

    @Test
    fun `given a year and isSndSemester when call get semester by year and isSndSemester then it should throw SemesterResponseDTO`() {
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

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(semesterMock)

        val actual = semesterService.getSemesterByYearAndIsSndSemester(2022, false)

        with(actual) {
            assertEquals(SEMESTER_ID, id)
            assertFalse(isSndSemester)
            assertEquals(2022, year)
            assertEquals(SEMESTER_NAME, name)
            assertEquals(LocalDateTime.MAX, acceptQuoteRequestsFrom)
            assertEquals(LocalDateTime.MAX, acceptQuoteRequestsTo)
            assertTrue(isCurrentSemester)
            assertTrue(isAcceptQuoteRequestsAvailable)
        }
    }

    @Test
    fun `given no semester when call get all then it should empty list of SemesterResponseDTO`() {
        every { semesterRepository.findAll() } returns emptyList()

        val actual = semesterService.getAll()

        assertTrue(actual.isEmpty())
    }

    @Test
    fun `given no semester when call get all then it should empty page of SemesterResponseDTO`() {
        every { semesterRepository.findAll(any<Pageable>()) } returns Page.empty()

        val actual = semesterService.getAll(mockk())

        assertTrue(actual.isEmpty)
    }

    @Test(expected = SemesterAlreadyExistException::class)
    fun `given a SemesterResponseDTO when call create semester exists then it should SemesterAlreadyExistException`() {
        val optionalSemesterMock = Optional.of(defaultSemester())

        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns optionalSemesterMock

       semesterService.createSemester(mockk(relaxed = true))
    }

    @Test(expected = InvalidRequestAcceptRequestQuotesDates::class)
    fun `given a SemesterResponseDTO invalid when call create semester then it should InvalidRequestAcceptRequestQuotesDates`() {
        val semester = defaultSemester()
        semester.acceptQuoteRequestsFrom = LocalDateTime.MAX
        semester.acceptQuoteRequestsTo = LocalDateTime.MIN
        val semesterRequestDTOMock = mockk<SemesterRequestDTO>(relaxed = true) {
            every { mapToSemester() } returns semester
        }
        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.empty()
        semesterService.createSemester(semesterRequestDTOMock)
    }

    @Test
    fun `given a SemesterResponseDTO valid when call create semester then it should SemesterResponseDTO and save semester`() {
        val semesterMock = mockk<Semester>(relaxed = true) {
            every { hasInvalidAcceptRequestQuotesDates() } returns false
            every { isSndSemester } returns false
            every { year } returns 2022
            every { acceptQuoteRequestsFrom } returns LocalDateTime.MAX
            every { acceptQuoteRequestsTo } returns LocalDateTime.MAX
        }
        val semesterRequestDTOMock = mockk<SemesterRequestDTO>(relaxed = true) {
            every { mapToSemester() } returns semesterMock
        }
        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.empty()
        every { semesterRepository.save(any()) } returns semesterMock

        val actual = semesterService.createSemester(semesterRequestDTOMock)

        with(actual) {
            assertFalse(isSndSemester)
            assertEquals(2022, year)
            assertEquals(LocalDateTime.MAX, acceptQuoteRequestsFrom)
            assertEquals(LocalDateTime.MAX, acceptQuoteRequestsTo)
        }
        verify { semesterRepository.save(semesterMock) }
    }

    @Test
    fun `given a id semester and UpdateSemesterRequestDTO when call update semester then it should update semester`() {
        val semesterMock = mockk<Semester> {
            every { id } returns SEMESTER_ID
            every { isSndSemester } returns false
            every { year } returns 2022
            every { name() } returns SEMESTER_NAME
            every { acceptQuoteRequestsFrom } returns LocalDateTime.MAX
            every { acceptQuoteRequestsTo } returns LocalDateTime.MAX
            every { isCurrent() } returns true
            every { isAcceptQuoteRequestsAvailable() } returns true
            every { acceptQuoteRequestsTo = any() } just runs
            every { acceptQuoteRequestsFrom = any() } just runs
            every { hasInvalidAcceptRequestQuotesDates() } returns false
        }
        val updateSemesterReqDTOMock = mockk<UpdateSemesterRequestDTO> {
            every { acceptQuoteRequestsFrom } returns LocalDateTime.MIN
            every { acceptQuoteRequestsTo } returns LocalDateTime.MIN
        }

        every { semesterRepository.findById(any()) } returns Optional.of(semesterMock)
        every { semesterRepository.save(any()) } returns semesterMock

        semesterService.updateSemester(SEMESTER_ID, updateSemesterReqDTOMock)

        verify { semesterRepository.save(semesterMock) }
    }

    private fun defaultSemester(): Semester {
        return Semester(id = 1, isSndSemester = true, year = 2000, acceptQuoteRequestsFrom = LocalDateTime.MIN, acceptQuoteRequestsTo = LocalDateTime.MAX)
    }
}
