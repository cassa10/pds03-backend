package ar.edu.unq.pds03backend.dto.user

import ar.edu.unq.pds03backend.model.Student
import ar.edu.unq.pds03backend.model.User
import javax.validation.constraints.*

class StudentRegisterRequestDTO(
    @field:NotBlank(message = "firstName mustn't be blank")
    val firstName: String,
    @field:NotBlank(message = "lastName mustn't be blank")
    val lastName: String,
    @field:NotNull(message = "dni mustn't be null")
    @field:Min(value = 0, message = "dni mustn't be lesser than 0")
    @field:Max(value = Int.MAX_VALUE.toLong(), message = "dni mustn't be greater than ${Int.MAX_VALUE.toLong()}")
    val dni: Int,
    @field:NotBlank(message = "email mustn't be blank")
    @field:Email(message = "invalid email")
    val email: String,
    var legajo: String = "",
) {
    fun getDni(): String = dni.toString()
    fun mapToUser(): User =
        Student(
            id = null,
            firstName = firstName,
            lastName = lastName,
            dni = getDni(),
            email = email,
            password = "",
            legajo = legajo,
            enrolledDegrees = mutableListOf(),
            degree_histories = listOf(),
            enrolledCourses = mutableListOf(),
        )
}