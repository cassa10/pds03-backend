package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO
import ar.edu.unq.pds03backend.dto.csv.CsvAcademyOfferRequestDTO
import ar.edu.unq.pds03backend.dto.csv.CsvStudentCourseRegistrationRequestDTO
import ar.edu.unq.pds03backend.dto.csv.CsvStudentWithDegreeDTO
import org.springframework.web.multipart.MultipartFile

interface ICsvService {
    fun parseAcademyHistoriesFile(file:MultipartFile): List<CsvAcademyHistoryRequestDTO>
    fun parseAcademyOfferFile(file: MultipartFile): List<CsvAcademyOfferRequestDTO>
    fun parseStudentsWithDegree(file: MultipartFile): List<CsvStudentWithDegreeDTO>
    fun parseStudentsCoursesRegistration(file: MultipartFile): List<CsvStudentCourseRegistrationRequestDTO>
}