package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Semester
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ISemesterRepository : JpaRepository<Semester, Long> {
    fun findByYearAndIsSndSemester(year: Int, isSndSemester: Boolean): Optional<Semester>
}