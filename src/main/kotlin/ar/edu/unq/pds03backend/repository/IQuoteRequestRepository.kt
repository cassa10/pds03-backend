package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface IQuoteRequestRepository : JpaRepository<QuoteRequest, Long> {
    fun findByCourseIdAndStudentId(idCourse: Long, idStudent: Long): Optional<QuoteRequest>
    fun findAllByCourseIdAndStudentId(idCourse: Long, idStudent: Long): Iterable<QuoteRequest>
    fun findAllByCourseId(idCourse: Long): Iterable<QuoteRequest>
    fun findAllByStudentId(idStudent: Long): Iterable<QuoteRequest>
    fun countByCourseId(courseId: Long): Int
    fun countByStateAndCourseId(quoteState: QuoteState, courseId: Long): Int
    fun countByStateAndStudentIdAndCourseSemesterId(quoteState: QuoteState, studentId: Long, semesterId: Long): Int

    @Query("SELECT DISTINCT q.student FROM QuoteRequest q WHERE q.state = :quoteState AND q.course.semester.id = :semesterId")
    fun findAllStudentsWithQuoteRequestStateAndCourseSemesterId(quoteState: QuoteState, semesterId: Long): List<Student>

    @Query("SELECT q FROM QuoteRequest q WHERE q.state = :quoteState AND q.course.semester.id = :semesterId")
    fun findAllByStateAndCourseSemesterId(quoteState: QuoteState, semesterId: Long): List<QuoteRequest>
}