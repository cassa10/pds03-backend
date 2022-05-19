package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

const val EMPTY_FILE_REASON = "empty file"
const val BAD_CSV_DATA = "csv data is wrong"

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = EMPTY_FILE_REASON)
class EmptyFileException : RuntimeException(EMPTY_FILE_REASON)

@ResponseStatus(HttpStatus.BAD_REQUEST, reason = BAD_CSV_DATA)
class CsvImportException(msg: String) : RuntimeException(msg)