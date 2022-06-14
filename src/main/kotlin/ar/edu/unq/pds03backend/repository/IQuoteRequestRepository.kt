package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Course
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.model.Student
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface IQuoteRequestRepository : JpaRepository<QuoteRequest, Long> {
    //counts
    fun countByCourseId(courseId: Long): Int
    fun countByStateAndCourseId(quoteState: QuoteState, courseId: Long): Int
    fun countByStateAndStudentIdAndCourseSemesterId(quoteState: QuoteState, studentId: Long, semesterId: Long): Int
    //filters
    fun findByCourseIdAndStudentId(idCourse: Long, idStudent: Long): Optional<QuoteRequest>
    fun findAllByCourseIdAndStudentId(idCourse: Long, idStudent: Long, sort: Sort): Iterable<QuoteRequest>
    fun findAllByCourseId(idCourse: Long, sort: Sort): Iterable<QuoteRequest>
    fun findAllByStudentIdAndCourseSemesterId(idStudent: Long, idSemester: Long, sort: Sort): List<QuoteRequest>
    fun findAllByStateAndStudentIdAndCourseSemesterId(state: QuoteState, idStudent: Long, semesterId: Long, sort: Sort): List<QuoteRequest>

    @Query("SELECT DISTINCT q.course FROM QuoteRequest q WHERE q.state = :state AND q.student.id = :idStudent AND q.course.semester.id = :semesterId")
    fun findAllCoursesByStateAndStudentIdAndCourseSemesterId(state: QuoteState, idStudent: Long, semesterId: Long): List<Course>

    @Query("SELECT DISTINCT q.student FROM QuoteRequest q WHERE q.state = :quoteState AND q.course.semester.id = :semesterId")
    fun findAllStudentsWithQuoteRequestStateAndCourseSemesterId(quoteState: QuoteState, semesterId: Long): List<Student>
    @Query("SELECT DISTINCT q.student FROM QuoteRequest q WHERE q.state = :quoteState AND q.course.subject.id = :idSubject AND q.course.semester.id = :semesterId")
    fun findAllStudentsWithQuoteRequestStateToSubjectAndCourseSemesterId(quoteState: QuoteState, idSubject: Long, semesterId: Long): List<Student>
    @Query("SELECT q FROM QuoteRequest q WHERE q.state = :quoteState AND q.course.semester.id = :semesterId")
    fun findAllByStateAndCourseSemesterId(quoteState: QuoteState, semesterId: Long, sort: Sort): List<QuoteRequest>
}