package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.subject.SubjectRequestDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectWithCoursesResponseDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ISubjectService {
    fun getById(id: Long): SubjectResponseDTO
    fun getAll(): List<SubjectResponseDTO>
    fun getAll(pageable: Pageable): Page<SubjectResponseDTO>
    fun create(subjectRequestDTO: SubjectRequestDTO)
    fun update(id: Long, subjectRequestDTO: SubjectRequestDTO)
    fun delete(id: Long)
    fun getAllCurrent(): List<SubjectWithCoursesResponseDTO>
    fun getAllCurrentByDegree(idDegree: Long): List<SubjectWithCoursesResponseDTO>
    fun getAllCurrentByStudent(idStudent: Long): List<SubjectWithCoursesResponseDTO>
}