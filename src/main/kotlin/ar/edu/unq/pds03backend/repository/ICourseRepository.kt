package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Course
import ar.edu.unq.pds03backend.model.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ICourseRepository : JpaRepository<Course, Long> {
    fun findBySemesterIdAndSubjectIdAndNumber(idSemester: Long, idSubject: Long, number: Int): Optional<Course>
    fun findBySubjectId(idSubject: Long): List<Course>
}