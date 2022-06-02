package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO
import org.springframework.web.multipart.MultipartFile

interface ICsvService {
    fun parseAcademyHistoriesFile(file:MultipartFile): List<CsvAcademyHistoryRequestDTO>
}