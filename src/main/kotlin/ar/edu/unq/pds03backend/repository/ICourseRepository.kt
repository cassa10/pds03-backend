package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Course
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ICourseRepository : CrudRepository<Course, Long> {
    fun findBySemesterIdAndSubjectIdAndNumber(idSemester: Long, idSubject: Long, number: Int): Optional<Course>
}