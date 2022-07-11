package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.degree.DegreeRequestDTO
import ar.edu.unq.pds03backend.exception.DegreeAlreadyExistsException
import ar.edu.unq.pds03backend.exception.DegreeNotFoundException
import ar.edu.unq.pds03backend.model.Degree
import ar.edu.unq.pds03backend.repository.IDegreeRepository
import ar.edu.unq.pds03backend.service.impl.DegreeService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class DegreeServiceTest {

    companion object {
        const val DEGREE_NAME = "Degree name"
        const val DEGREE_ACRONYM = "Degree acronym"
        const val DEGREE_ID: Long = 1
    }

    @RelaxedMockK
    private lateinit var degreeRepository: IDegreeRepository

    private lateinit var degreeService: DegreeService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        degreeService = DegreeService(degreeRepository)
    }

    @Test(expected = DegreeAlreadyExistsException::class)
    fun `given a degree already exist when call create degree then it should throw DegreeAlreadyExistsException`() {
        val optionalDegreeMock = Optional.of(defaultDegree())
        every { degreeRepository.findByNameAndAcronym(any(), any()) } returns optionalDegreeMock

        degreeService.create(mockk(relaxed = true))
    }

    @Test
    fun `given a new degree when call create degree then it should create degree success`() {
        val degreeMock = mockk<Degree> {
            every { name } returns DEGREE_NAME
            every { acronym } returns DEGREE_ACRONYM
            every { subjects } returns mutableListOf()
        }
        val degreeRequestDTOMock = mockk<DegreeRequestDTO> {
            every { name } returns DEGREE_NAME
            every { acronym } returns DEGREE_ACRONYM
        }
        val optionalDegreeMock = Optional.empty<Degree>()
        every { degreeRepository.findByNameAndAcronym(any(), any()) } returns optionalDegreeMock
        every { degreeRepository.save(any()) } returns degreeMock

        degreeService.create(degreeRequestDTOMock)

        verify { degreeRepository.save(any()) }
    }

    @Test(expected = DegreeNotFoundException::class)
    fun `given a degree not found when call get by id then id should DegreeNotFoundException`() {
        val optionalDegreeMock = Optional.empty<Degree>()

        every { degreeRepository.findById(any()) } returns optionalDegreeMock

        degreeService.getById(DEGREE_ID)
    }

    @Test
    fun `given a degree id when call get by id then it should return DegreeResponseDTO`() {
        val degreeMock = mockk<Degree> {
            every { id } returns DEGREE_ID
            every { name } returns DEGREE_NAME
            every { acronym } returns DEGREE_ACRONYM
            every { subjects } returns mutableListOf()
        }

        every { degreeRepository.findById(any()) } returns Optional.of(degreeMock)

        val actual = degreeService.getById(DEGREE_ID)

        with(actual) {
            assertEquals(DEGREE_ID, id)
            assertEquals(DEGREE_NAME, name)
            assertEquals(DEGREE_ACRONYM, acronym)
            assertTrue(subjects.isEmpty())
        }
    }

    @Test
    fun `given no degree when call get all then it should list empty`() {
        every { degreeRepository.findAll() } returns emptyList()

        val actual = degreeService.getAll()

        assertTrue(actual.isEmpty())
    }

    @Test
    fun `given a degrees when call get all then it should list of DegreeResponseDTO`() {
        every { degreeRepository.findAll() } returns listOf(mockk(relaxed = true))

        val actual = degreeService.getAll()

        assertFalse(actual.isEmpty())
    }

    @Test(expected = DegreeNotFoundException::class)
    fun `given a degree not found when call update then id should DegreeNotFoundException`() {
        val optionalDegreeMock = Optional.empty<Degree>()

        every { degreeRepository.findById(any()) } returns optionalDegreeMock

        degreeService.update(DEGREE_ID, mockk())
    }

    @Test
    fun `given a degree exist when call update then id should update degree`() {
        val degreeMock = mockk<Degree>(relaxed = true) {
            every { name } returns DEGREE_NAME
            every { acronym } returns DEGREE_ACRONYM
        }
        val degreeRequestDTOMock = mockk<DegreeRequestDTO> {
            every { name } returns DEGREE_NAME
            every { acronym } returns DEGREE_ACRONYM
        }

        every { degreeRepository.findById(any()) } returns Optional.of(degreeMock)
        every { degreeRepository.save(any()) } returns degreeMock

        degreeService.update(DEGREE_ID, degreeRequestDTOMock)

        verify { degreeRepository.save(any()) }
    }

    @Test(expected = DegreeNotFoundException::class)
    fun `given a degree not found when call delete then id should DegreeNotFoundException`() {
        val optionalDegreeMock = Optional.empty<Degree>()

        every { degreeRepository.findById(any()) } returns optionalDegreeMock

        degreeService.delete(DEGREE_ID)
    }

    @Test
    fun `given a degree when call delete then id should delete degree`() {
        val degreeMock = mockk<Degree> {
            every { name } returns DEGREE_NAME
            every { acronym } returns DEGREE_ACRONYM
        }

        every { degreeRepository.findById(any()) } returns Optional.of(degreeMock)

        degreeService.delete(DEGREE_ID)

        verify { degreeRepository.delete(degreeMock) }
    }

    private fun defaultDegree(): Degree =
        Degree(
            id = 1,
            name = "",
            acronym = "",
            subjects = mutableListOf(),
        )
}
