package ar.edu.unq.pds03backend.model

class Student(
    val id: Long?,
    val dni: String,
    val legajo: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val registered_careers: Collection<Career>,
    val studied_subjects: Collection<StudiedSubject>
)
