package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.academyHistory.StudiedDegreeDTO
import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IAcademyHistoryService {
    fun updateAcademyHistory(data: List<CsvAcademyHistoryRequestDTO>)
    fun getAllStudiedDegrees(): List<StudiedDegreeDTO>
    fun getAllStudiedDegrees(pageable: Pageable): Page<StudiedDegreeDTO>
    fun getAllStudiedDegreesByStudent(idStudent: Long): List<StudiedDegreeDTO>
    fun getAllStudiedDegreesByStudent(idStudent: Long, pageable: Pageable): Page<StudiedDegreeDTO>
    fun getAllStudiedDegreesByDegree(idDegree: Long): List<StudiedDegreeDTO>
    fun getAllStudiedDegreesByDegree(idDegree: Long, pageable: Pageable): Page<StudiedDegreeDTO>
    fun getAllStudiedDegreesByStudentAndDegree(idStudent: Long, idDegree: Long): List<StudiedDegreeDTO>
    fun getAllStudiedDegreesByStudentAndDegree(idStudent: Long, idDegree: Long, pageable: Pageable): Page<StudiedDegreeDTO>
}
