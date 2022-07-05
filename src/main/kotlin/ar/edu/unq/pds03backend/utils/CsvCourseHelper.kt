package ar.edu.unq.pds03backend.utils

object CsvCourseHelper {
    fun getExternalId(rawStringFromCsv: String): String = try {
        rawStringFromCsv.split(" ").first()
    }catch (e:Exception){
        rawStringFromCsv
    }
}