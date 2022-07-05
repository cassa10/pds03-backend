package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import javax.persistence.NamedNativeQuery

interface ICourseRepository : JpaRepository<Course, Long> {
    fun findBySubjectId(idSubject: Long): List<Course>
    fun findAllBySemesterIdAndSubjectId(idSemester:Long, idSubject: Long): List<Course>
    fun findAllBySemesterId(idSemester:Long): List<Course>
    fun findByExternalIdAndSemesterIdAndSubjectId(externalId: String, idSemester: Long, idSubject: Long): Optional<Course>
    fun findByExternalIdAndSemesterId(externalId: String, idSemester: Long): Optional<Course>

    @Query("SELECT COUNT(course.id) FROM Student student INNER JOIN student.enrolledCourses AS course WHERE :courseId = course.id")
    fun countByEnrolledStudentsInCourse(courseId: Long): Int
}