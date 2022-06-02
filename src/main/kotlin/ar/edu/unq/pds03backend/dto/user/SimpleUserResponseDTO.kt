package ar.edu.unq.pds03backend.dto.user

import ar.edu.unq.pds03backend.dto.degree.SimpleDegreeResponseDTO
import ar.edu.unq.pds03backend.model.Course
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.QuoteState

data class UserResponseDTO(
    val id: Long,
    val isStudent: Boolean,
    val username: String,
    val firstName: String,
    val lastName: String,
    val dni: String,
    val email: String,
    val legajo: String,
    val enrolledDegrees: List<SimpleDegreeResponseDTO>,
    val enrolledSubjects: List<SimpleEnrolledSubjectsDataDTO>,
    val requestedSubjects: List<RequestedSubjectsDTO>
)

class SimpleEnrolledSubjectsDataDTO(
    //Id subject
    val id: Long,
    val name: String,
    val course: SimpleCourseDataDTO,
){
    class Mapper(private val course: Course) {
        fun map(): SimpleEnrolledSubjectsDataDTO = SimpleEnrolledSubjectsDataDTO(
            id = course.subject.id!!,
            name = course.subject.name,
            course = SimpleCourseDataDTO(idCourse = course.id!!, name = course.name, assigned_teachers = course.assigned_teachers)
        )
    }
}

class RequestedSubjectsDTO(
    //Id subject
    val id: Long,
    val name: String,
    val course: SimpleCourseDataDTO,
    val idQuoteRequest: Long,
    val status: QuoteState,
    val comment: String,
    val adminComment: String,
) {
    class Mapper(private val quoteRequest: QuoteRequest) {
        fun map(): RequestedSubjectsDTO = RequestedSubjectsDTO(
            id = quoteRequest.course.subject.id!!,
            name = quoteRequest.course.subject.name,
            course = SimpleCourseDataDTO(idCourse = quoteRequest.course.id!!, name = quoteRequest.course.name, assigned_teachers = quoteRequest.course.assigned_teachers),
            idQuoteRequest = quoteRequest.id!!,
            status = quoteRequest.state,
            comment = quoteRequest.comment,
            adminComment = quoteRequest.adminComment,
        )
    }
}

data class SimpleCourseDataDTO(
    val idCourse: Long,
    val name: String,
    val assigned_teachers: String,
)

