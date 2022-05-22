package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.Semester

interface ISemesterService {
    fun getSemesterByYearAndIsSndSemester(year: Int, isSndSemester: Boolean): Semester
}