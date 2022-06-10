package ar.edu.unq.pds03backend

import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.repository.ICourseRepository
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.repository.IStudentRepository
import ar.edu.unq.pds03backend.service.IQuoteRequestService
import ar.edu.unq.pds03backend.service.impl.QuoteRequestService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

private const val QUOTE_REQUEST_ID = 1

class QuoteRequestServiceTest {

    @RelaxedMockK
    private lateinit var quoteRequestRepository: IQuoteRequestRepository

    @RelaxedMockK
    private lateinit var courseRepository: ICourseRepository

    @RelaxedMockK
    private lateinit var studentRepository: IStudentRepository

    @RelaxedMockK
    private lateinit var semesterRepository: ISemesterRepository

    private lateinit var quoteRequest: QuoteRequest

    private lateinit var quoteRequestService: IQuoteRequestService

    private lateinit var actualAnswer: QuoteState

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { quoteRequestRepository.findById(QUOTE_REQUEST_ID.toLong()) } returns Optional.of(quoteRequest)
        quoteRequestService = QuoteRequestService(quoteRequestRepository, courseRepository, studentRepository, semesterRepository)
    }

    @Test
    fun `given a quote request id when accept quote request then it should change the status of the request to approved`() {

        val actual = quoteRequestService.getById(1)
    }
}
