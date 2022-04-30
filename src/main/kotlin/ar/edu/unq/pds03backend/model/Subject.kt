package ar.edu.unq.pds03backend.model

class Subject(
    val id: Long?,
    val name: String,
    val degree: Collection<Career>,
)
