package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO
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
class CsvService: ICsvService {
    private val logger = KotlinLogging.logger { }
    private val defaultCsvImportException = CsvImportException("error during csv import")

    override fun uploadCsvFile(file: MultipartFile) {
        validateFileIsNotEmpty(file)
        var fileReader: BufferedReader? = null
        try {
            fileReader = BufferedReader(InputStreamReader(file.inputStream))
            val data:List<CsvAcademyHistoryRequestDTO> = createCSVToBean(fileReader).parse()
            logger.info("csv-data-parsed: $data")
            //TODO: Map csv data to entity objects and save all

        } catch (ex: Exception) {
            logger.error(ex.message)
            throw defaultCsvImportException
        } finally {
            closeFileReader(fileReader)
        }
    }

    private fun validateFileIsNotEmpty(file: MultipartFile) {
        if (file.isEmpty) throw EmptyFileException()
    }

    private fun createCSVToBean(fileReader: BufferedReader?): CsvToBean<CsvAcademyHistoryRequestDTO> =
        CsvToBeanBuilder<CsvAcademyHistoryRequestDTO>(fileReader)
            .withType(CsvAcademyHistoryRequestDTO::class.java)
            .withIgnoreLeadingWhiteSpace(true)
            .build()

    private fun closeFileReader(fileReader: BufferedReader?) {
        try {
            fileReader!!.close()
        } catch (ex: IOException) {
            throw defaultCsvImportException
        }
    }
}