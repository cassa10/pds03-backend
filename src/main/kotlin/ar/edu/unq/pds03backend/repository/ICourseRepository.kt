package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Course
import org.springframework.data.jpa.repository.JpaRepository

interface ICourseRepository : JpaRepository<Course, Long> {
    fun findBySubjectId(idSubject: Long): List<Course>
    fun findAllBySemesterIdAndSubjectId(idSemester:Long, idSubject: Long): List<Course>
}