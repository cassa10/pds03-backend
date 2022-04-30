package ar.edu.unq.pds03backend.model

class Course(
    val semester: Semester,
    val subject: Subject,
    val number: Int,
    val code: String,
    val name: String,
    val current_quotes: Int,
    val total_quotes: Int,
    val students: Collection<Student>
)
