package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO
import ar.edu.unq.pds03backend.dto.csv.CsvAcademyOfferRequestDTO
import ar.edu.unq.pds03backend.dto.csv.CsvStudentWithDegreeDTO
import ar.edu.unq.pds03backend.dto.csv.CsvStudentRequestDTO
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

@Service
class CsvService(
    @Autowired private val degreeService: IDegreeService
) : ICsvService {
    companion object {
        private val logger = KotlinLogging.logger { }
        private val defaultCsvImportException = CsvImportException("error during csv import")
    }

    override fun parseAcademyHistoriesFile(file: MultipartFile): List<CsvAcademyHistoryRequestDTO> =
        parseCsv(file) { createCSVAcademyHistoriesToBean(it).parse() }

    override fun parseAcademyOfferFile(file: MultipartFile): List<CsvAcademyOfferRequestDTO> =
        parseCsv(file) { createCSVAcademyOfferToBean(it).parse() }

    override fun parseStudents(file: MultipartFile): List<CsvStudentWithDegreeDTO> {
        val parsedStudents = parseCsv(file) { createCSVUserToBean(it).parse() }
        var degree: Degree? = null
        return parsedStudents.sortedBy { it.degree }.map {
            if (degree == null || degree!!.acronym != it.getDegreeAcronym()){
                degree = getDegreeByAcronym(it.getDegreeAcronym())
            }
            it.map(degree!!)
        }
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

    //TODO Refactor: mapper to class with interface
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

    private fun createCSVUserToBean(fileReader: BufferedReader?): CsvToBean<CsvStudentRequestDTO> =
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