package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.QuoteRequest
import org.springframework.data.repository.CrudRepository
import java.util.*

interface IQuoteRequestRepository : CrudRepository<QuoteRequest, Long> {
    fun findByCourseIdAndStudentId(idCourse: Long, idStudent: Long): Optional<QuoteRequest>
}