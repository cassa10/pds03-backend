package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.semester.SemesterRequestDTO
import ar.edu.unq.pds03backend.dto.semester.SemesterResponseDTO
import ar.edu.unq.pds03backend.dto.semester.UpdateSemesterRequestDTO
import ar.edu.unq.pds03backend.model.Semester
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ISemesterService {
    fun getSemesterById(idSemester: Long): SemesterResponseDTO
    fun getSemesterByYearAndIsSndSemester(year: Int, isSndSemester: Boolean): SemesterResponseDTO
    fun createSemester(semesterRequestDTO: SemesterRequestDTO): SemesterResponseDTO
    fun updateSemester(idSemester: Long, updateSemesterReqDTO: UpdateSemesterRequestDTO)
    fun getAll(): List<SemesterResponseDTO>
    fun getAll(pageable: Pageable): Page<SemesterResponseDTO>
}