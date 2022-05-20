package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.QuoteState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.*

interface IQuoteRequestRepository : JpaRepository<QuoteRequest, Long> {
    fun findByCourseIdAndStudentId(idCourse: Long, idStudent: Long): Optional<QuoteRequest>
    fun findAllByCourseIdAndStudentId(idCourse: Long, idStudent: Long): Iterable<QuoteRequest>
    fun findAllByCourseId(idCourse: Long): Iterable<QuoteRequest>
    fun findAllByStudentId(idStudent: Long): Iterable<QuoteRequest>
    fun countByStateAndCourseId(quoteState: QuoteState, courseId: Long): Int
}