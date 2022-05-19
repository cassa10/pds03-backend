package ar.edu.unq.pds03backend.service

import org.springframework.web.multipart.MultipartFile

interface ICsvService {
    fun uploadCsvFile(file:MultipartFile)
}