package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.csv.*
import ar.edu.unq.pds03backend.exception.CsvImportException
import ar.edu.unq.pds03backend.exception.EmptyFileException
import ar.edu.unq.pds03backend.model.Degree
import ar.edu.unq.pds03backend.service.ICsvService
import ar.edu.unq.pds03backend.service.IDegreeService
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringReader
import java.util.stream.Collectors

@Service
class CsvService(
    @Autowired private val degreeService: IDegreeService
) : ICsvService {
    companion object {
        private val logger = KotlinLogging.logger { }
        private val defaultCsvImportException = CsvImportException("error during csv import")
        const val TPI_DEGREE_ACRONYM = "TPI"
        const val LI_DEGREE_ACRONYM = "LI"
        const val TPI_DEGREE_FROM_SUBJECTS_CSV = "TPI"
    }

    override fun parseAcademyHistoriesFile(file: MultipartFile): List<CsvAcademyHistoryRequestDTO> =
        parseCsv(file) { createCSVAcademyHistoriesToBean(it).parse() }

    override fun parseAcademyOfferFile(file: MultipartFile): List<CsvAcademyOfferRequestDTO> =
        parseCsv(file) { createCSVAcademyOfferToBean(it).parse() }

    override fun parseStudentsWithDegree(file: MultipartFile): List<CsvStudentWithDegreeDTO> {
        val parsedStudents = parseCsv(file) { createCSVStudentToBean(it).parse() }
        var degree: Degree? = null
        return parsedStudents.sortedBy { it.degree }.map {
            if (degree == null || degree!!.acronym != it.getDegreeAcronym()){
                degree = getDegreeByAcronym(it.getDegreeAcronym())
            }
            it.map(degree!!)
        }
    }

    override fun parseStudentsCoursesRegistration(file: MultipartFile): List<CsvStudentCourseRegistrationRequestDTO> =
        parseCsv(file) { createCSVStudentCourseRegistrationToBean(it).parse() }

    override fun parseSubjectsFile(file: MultipartFile): List<CsvSubjectWithPrerequisite> {
        val data = parseCsv(file) {
            val parsedFile =
                it!!.lines().collect(Collectors.joining("\n"))
                    .replace("\"", "")
                    .replace(",(?=((?!\\{).)*?})".toRegex(),";")
            createCSVSubjectsToBean(BufferedReader(StringReader(parsedFile))).parse()
        }.toMutableList()
        val header = data.removeAt(0)
        val degree = getDegreeByAcronym(getDegreeAcronymOfSubjectsCsv(header))
        //TODO: Add module handling
        return data.mapNotNull { it.mapToCsvSubjectWithPrerequisite(degree) }
    }

    private fun getDegreeAcronymOfSubjectsCsv(header: CsvSubjectsRequestDTO): String {
        val acronymCsv = header.module.split(" ")[1]
        if(acronymCsv == TPI_DEGREE_FROM_SUBJECTS_CSV){
            return TPI_DEGREE_ACRONYM
        }
        return LI_DEGREE_ACRONYM
    }

    private fun getDegreeByAcronym(acronym: String): Degree {
        try {
            return degreeService.findByAcronym(acronym)
        } catch (e: Exception) {
            logger.error("error when find degree with acronym: ${acronym}, exception message: ${e.message}")
            //Throw exception anyway
            throw e
        }
    }

    private fun <T> parseCsv(file: MultipartFile, mapper: (BufferedReader?) -> List<T>): List<T> {
        validateFileIsNotEmpty(file)
        var fileReader: BufferedReader? = null
        try {
            fileReader = BufferedReader(InputStreamReader(file.inputStream))
            val data: List<T> = mapper(fileReader)
            logger.info("csv-data-parsed: $data")
            return data
        } catch (ex: Exception) {
            logger.error(ex.message)
            throw defaultCsvImportException
        } finally {
            closeFileReader(fileReader)
        }
    }

    private fun createCSVStudentToBean(fileReader: BufferedReader?): CsvToBean<CsvStudentRequestDTO> =
        CsvToBeanBuilder<CsvStudentRequestDTO>(fileReader)
            .withType(CsvStudentRequestDTO::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()

    private fun createCSVAcademyHistoriesToBean(fileReader: BufferedReader?): CsvToBean<CsvAcademyHistoryRequestDTO> =
        CsvToBeanBuilder<CsvAcademyHistoryRequestDTO>(fileReader)
            .withType(CsvAcademyHistoryRequestDTO::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()

    private fun createCSVAcademyOfferToBean(fileReader: BufferedReader?): CsvToBean<CsvAcademyOfferRequestDTO> =
        CsvToBeanBuilder<CsvAcademyOfferRequestDTO>(fileReader)
            .withType(CsvAcademyOfferRequestDTO::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()

    private fun createCSVStudentCourseRegistrationToBean(fileReader: BufferedReader?): CsvToBean<CsvStudentCourseRegistrationRequestDTO> =
        CsvToBeanBuilder<CsvStudentCourseRegistrationRequestDTO>(fileReader)
            .withType(CsvStudentCourseRegistrationRequestDTO::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()

    private fun createCSVSubjectsToBean(fileReader: BufferedReader?): CsvToBean<CsvSubjectsRequestDTO> =
        CsvToBeanBuilder<CsvSubjectsRequestDTO>(fileReader)
            .withType(CsvSubjectsRequestDTO::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()

    private fun validateFileIsNotEmpty(file: MultipartFile) {
        if (file.isEmpty) throw EmptyFileException()
    }

    private fun closeFileReader(fileReader: BufferedReader?) {
        try {
            fileReader!!.close()
        } catch (ex: IOException) {
            throw defaultCsvImportException
        }
    }
}