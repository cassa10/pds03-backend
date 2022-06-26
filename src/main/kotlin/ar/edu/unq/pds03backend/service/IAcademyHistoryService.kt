package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.academyHistory.StudiedDegreeDTO
import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO

interface IAcademyHistoryService {
    fun updateAcademyHistory(data: List<CsvAcademyHistoryRequestDTO>)
    fun getAllStudiedDegrees(): List<StudiedDegreeDTO>
    fun getAllStudiedDegreesByStudent(idStudent: Long): List<StudiedDegreeDTO>
    fun getAllStudiedDegreesByDegree(idDegree: Long): List<StudiedDegreeDTO>
    fun getAllStudiedDegreesByStudentAndDegree(idStudent: Long, idDegree: Long): List<StudiedDegreeDTO>
}