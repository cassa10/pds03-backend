package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.csv.*
import org.springframework.web.multipart.MultipartFile

interface ICsvService {
    fun parseAcademyHistoriesFile(file:MultipartFile): List<CsvAcademyHistoryRequestDTO>
    fun parseAcademyOfferFile(file: MultipartFile): List<CsvAcademyOfferRequestDTO>
    fun parseStudentsWithDegree(file: MultipartFile): List<CsvStudentWithDegreeDTO>
    fun parseStudentsCoursesRegistration(file: MultipartFile): List<CsvStudentCourseRegistrationRequestDTO>
    fun parseSubjectsFile(file: MultipartFile): List<CsvSubjectWithPrerequisite>
}