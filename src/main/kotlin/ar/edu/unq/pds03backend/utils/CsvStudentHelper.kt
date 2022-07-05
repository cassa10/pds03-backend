package ar.edu.unq.pds03backend.utils

object CsvStudentHelper {
    fun getDni(rawStudentDni: String): String = try {
        rawStudentDni.split(" ")[1]
    } catch (e: Exception) {
        rawStudentDni
    }

}