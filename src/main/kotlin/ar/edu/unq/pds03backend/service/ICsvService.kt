package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO
import ar.edu.unq.pds03backend.dto.csv.CsvAcademyOfferRequestDTO
import ar.edu.unq.pds03backend.model.Student
import org.springframework.web.multipart.MultipartFile

interface ICsvService {
    fun parseAcademyHistoriesFile(file:MultipartFile): List<CsvAcademyHistoryRequestDTO>
    fun parseAcademyOfferFile(file: MultipartFile): List<CsvAcademyOfferRequestDTO>
    fun parseStudents(file: MultipartFile): List<Student>
}