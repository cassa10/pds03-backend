package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.academyHistory.StudiedDegreeDTO
import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO

interface IAcademyHistoryService {
    fun updateAcademyHistory(data: List<CsvAcademyHistoryRequestDTO>)
    fun getAllStudiedDegrees(): List<StudiedDegreeDTO>
}