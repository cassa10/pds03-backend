package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO
import ar.edu.unq.pds03backend.dto.csv.CsvAcademyOfferRequestDTO
import ar.edu.unq.pds03backend.exception.CsvImportException
import ar.edu.unq.pds03backend.exception.EmptyFileException
import ar.edu.unq.pds03backend.service.ICsvService
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

@Service
class CsvService(): ICsvService {
    private val logger = KotlinLogging.logger { }
    private val defaultCsvImportException = CsvImportException("error during csv import")

    override fun parseAcademyHistoriesFile(file: MultipartFile): List<CsvAcademyHistoryRequestDTO> {
        validateFileIsNotEmpty(file)
        var fileReader: BufferedReader? = null
        try {
            fileReader = BufferedReader(InputStreamReader(file.inputStream))
            val data: List<CsvAcademyHistoryRequestDTO> = createCSVAcademyHistoriesToBean(fileReader).parse()
            logger.info("csv-data-parsed: $data")
            return data
        } catch (ex: Exception) {
            logger.error(ex.message)
            throw defaultCsvImportException
        } finally {
            closeFileReader(fileReader)
        }
    }

    override fun parseAcademyOfferFile(file: MultipartFile): List<CsvAcademyOfferRequestDTO> {
        validateFileIsNotEmpty(file)
        var fileReader: BufferedReader? = null
        try {
            fileReader = BufferedReader(InputStreamReader(file.inputStream))
            val data: List<CsvAcademyOfferRequestDTO> = createCSVAcademyOfferToBean(fileReader).parse()
            logger.info("csv-data-parsed: $data")
            return data
        } catch (ex: Exception) {
            logger.error(ex.message)
            throw defaultCsvImportException
        } finally {
            closeFileReader(fileReader)
        }
    }

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