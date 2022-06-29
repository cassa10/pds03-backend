package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Course
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ICourseRepository : JpaRepository<Course, Long> {
    fun findBySubjectId(idSubject: Long): List<Course>
    fun findAllBySemesterIdAndSubjectId(idSemester:Long, idSubject: Long): List<Course>
    fun findAllBySemesterId(idSemester:Long): List<Course>
    fun findAllBySemesterId(idSemester:Long, pageable: Pageable): Page<Course>
    fun findByNameAndSemesterIdAndSubjectId(name: String, idSemester: Long, idSubject: Long): Optional<Course>
}