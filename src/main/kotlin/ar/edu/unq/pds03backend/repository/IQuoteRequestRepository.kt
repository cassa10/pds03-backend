package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface IQuoteRequestRepository : JpaRepository<QuoteRequest, Long> {
    //counts
    fun countByCourseId(courseId: Long): Int

    @Query("SELECT COUNT(q) FROM QuoteRequest q WHERE q.course.id = :courseId AND q.state IN (:quoteStates)")
    fun countByInStatesAndCourseId(quoteStates: Set<QuoteState>, courseId: Long): Int

    @Query("SELECT COUNT(q) FROM QuoteRequest q WHERE q.student.id = :studentId AND q.course.semester.id = :semesterId AND q.state IN (:quoteStates)")
    fun countByInStatesAndStudentIdAndCourseSemesterId(quoteStates: Set<QuoteState>, studentId: Long, semesterId: Long): Int

    @Query("SELECT COUNT(q) FROM QuoteRequest q WHERE q.course.semester.id = :semesterId AND :degree MEMBER OF q.course.subject.degrees AND q.state IN (:quoteStates)")
    fun countByDegreeAndSemesterIdAndInQuoteStates(quoteStates: Set<QuoteState>, semesterId: Long, degree: Degree): Int

    @Query("SELECT COUNT(q) FROM QuoteRequest q WHERE q.course.semester.id = :semesterId AND :studentId = q.student.id AND :degree MEMBER OF q.course.subject.degrees AND q.state IN (:quoteStates)")
    fun countByDegreeAndStudentIdAndSemesterIdAndInQuoteStates(quoteStates: Set<QuoteState>, studentId: Long, semesterId: Long, degree: Degree): Int

    //filters
    fun findByCourseIdAndStudentId(idCourse: Long, idStudent: Long): Optional<QuoteRequest>

    @Query("SELECT q FROM QuoteRequest q WHERE q.student.id = :idStudent AND q.course.semester.id = :semesterId AND q.state IN (:quoteStates)")
    fun findAllByInStatesAndStudentIdAndCourseSemesterId(quoteStates: Set<QuoteState>, idStudent: Long, semesterId: Long, sort: Sort): List<QuoteRequest>

    @Query("SELECT q FROM QuoteRequest q WHERE NOT(q.id = :idQuoteRequestToSkip) AND q.student.id = :idStudent AND q.course.semester.id = :semesterId AND q.state IN (:quoteStates)")
    fun findAllByInStatesAndSkipIdAndStudentIdAndCourseSemesterId(quoteStates: Set<QuoteState>, idQuoteRequestToSkip: Long, idStudent: Long, semesterId: Long, sort: Sort): List<QuoteRequest>

    @Query("SELECT q FROM QuoteRequest q WHERE q.state IN (:quoteStates)")
    fun findAllByInStates(quoteStates: Set<QuoteState>, sort: Sort): Iterable<QuoteRequest>

    @Query("SELECT q FROM QuoteRequest q WHERE q.state IN (:quoteStates) ORDER BY q.createdOn")
    fun findAllByInStates(quoteStates: Set<QuoteState>, pageable: Pageable): Page<QuoteRequest>

    @Query("SELECT q FROM QuoteRequest q WHERE q.course.id = :idCourse AND q.student.id = :idStudent AND q.state IN (:quoteStates)")
    fun findAllByCourseIdAndStudentIdAndInStates(idCourse: Long, idStudent: Long, quoteStates: Set<QuoteState>, sort: Sort): Iterable<QuoteRequest>

    @Query("SELECT q FROM QuoteRequest q WHERE q.course.id = :idCourse AND q.student.id = :idStudent AND q.state IN (:quoteStates) ORDER BY q.createdOn")
    fun findAllByCourseIdAndStudentIdAndInStates(idCourse: Long, idStudent: Long, quoteStates: Set<QuoteState>, pageable: Pageable): Page<QuoteRequest>

    @Query("SELECT q FROM QuoteRequest q WHERE q.course.id = :idCourse AND q.state IN (:quoteStates)")
    fun findAllByCourseIdAndInStates(idCourse: Long, quoteStates: Set<QuoteState>, sort: Sort): Iterable<QuoteRequest>

    @Query("SELECT q FROM QuoteRequest q WHERE q.course.id = :idCourse AND q.state IN (:quoteStates) ORDER BY q.createdOn")
    fun findAllByCourseIdAndInStates(idCourse: Long, quoteStates: Set<QuoteState>, pageable: Pageable): Page<QuoteRequest>

    @Query("SELECT q FROM QuoteRequest q WHERE q.student.id = :idStudent AND q.course.semester.id = :idSemester AND q.state IN (:quoteStates)")
    fun findAllByStudentIdAndCourseSemesterIdAndInStates(idStudent: Long, idSemester: Long, quoteStates: Set<QuoteState>, sort: Sort): List<QuoteRequest>

    @Query("SELECT q FROM QuoteRequest q WHERE q.student.id = :idStudent AND q.course.semester.id = :idSemester AND q.state IN (:quoteStates) ORDER BY q.createdOn")
    fun findAllByStudentIdAndCourseSemesterIdAndInStates(idStudent: Long, idSemester: Long, quoteStates: Set<QuoteState>, pageable: Pageable): Page<QuoteRequest>

    @Query("SELECT DISTINCT q.course FROM QuoteRequest q WHERE q.student.id = :idStudent AND q.course.semester.id = :semesterId AND q.state IN (:quoteStates)")
    fun findAllCoursesWithQuoteRequestInStatesAndStudentIdAndCourseSemesterId(quoteStates: Set<QuoteState>, idStudent: Long, semesterId: Long): List<Course>

    @Query("SELECT DISTINCT q.student FROM QuoteRequest q WHERE q.course.semester.id = :semesterId AND q.state IN (:quoteStates)")
    fun findAllStudentsWithQuoteRequestInStatesAndCourseSemesterId(quoteStates: Set<QuoteState>, semesterId: Long): List<Student>

    @Query("SELECT DISTINCT q.student FROM QuoteRequest q WHERE q.course.semester.id = :semesterId AND q.state IN (:quoteStates)")
    fun findAllStudentsWithQuoteRequestInStatesAndCourseSemesterId(quoteStates: Set<QuoteState>, semesterId: Long, pageable: Pageable): Page<Student>

    @Query("SELECT DISTINCT q.student FROM QuoteRequest q WHERE q.course.subject.id = :idSubject AND q.course.semester.id = :semesterId AND q.state IN (:quoteStates)")
    fun findAllStudentsWithQuoteRequestInStatesAndSubjectIdAndCourseSemesterId(quoteStates: Set<QuoteState>, idSubject: Long, semesterId: Long): List<Student>

    @Query("SELECT DISTINCT q.student FROM QuoteRequest q WHERE q.course.subject.id = :idSubject AND q.course.semester.id = :semesterId AND q.state IN (:quoteStates)")
    fun findAllStudentsWithQuoteRequestInStatesAndSubjectIdAndCourseSemesterId(quoteStates: Set<QuoteState>, idSubject: Long, semesterId: Long, pageable: Pageable): Page<Student>

    @Query("SELECT q FROM QuoteRequest q WHERE q.course.semester.id = :semesterId AND q.state IN (:quoteStates)")
    fun findAllByInStatesAndCourseSemesterId(quoteStates: Set<QuoteState>, semesterId: Long, sort: Sort): List<QuoteRequest>
}