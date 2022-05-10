package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.course.CourseResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.semester.SemesterResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentResponseDTO
import ar.edu.unq.pds03backend.dto.subject.SubjectResponseDTO
import ar.edu.unq.pds03backend.dto.user.UserResponseDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.repository.ICourseRepository
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.repository.IStudentRepository
import ar.edu.unq.pds03backend.service.IQuoteRequestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuoteRequestService(
    @Autowired private val quoteRequestRepository: IQuoteRequestRepository,
    @Autowired private val courseRepository: ICourseRepository,
    @Autowired private val studentRepository: IStudentRepository
) : IQuoteRequestService {

    override fun create(code: String, quoteRequestRequestDTO: QuoteRequestRequestDTO) {
        val (idSemester, idSubject, number) = decode(code)
        val course = courseRepository.findBySemesterIdAndSubjectIdAndNumber(idSemester, idSubject, number)
        if (!course.isPresent) throw CourseNotFoundException()

        val student = studentRepository.findById(quoteRequestRequestDTO.idStudent)
        if (!student.isPresent) throw StudentNotFoundException()

        val quoteRequest = quoteRequestRepository.findByCourseIdAndStudentId(course.get().id!!, student.get().id!!)
        if (quoteRequest.isPresent) throw QuoteRequestAlreadyExistsException()

        quoteRequestRepository.save(
            QuoteRequest(
                course = course.get(),
                student = student.get(),
                state = QuoteState.PENDING,
                comment = quoteRequestRequestDTO.comment
            )
        )
    }

    override fun getAllByCourse(code: String): List<QuoteRequestResponseDTO> {
        val (idSemester, idSubject, number) = decode(code)
        val course = courseRepository.findBySemesterIdAndSubjectIdAndNumber(idSemester, idSubject, number)
        if (!course.isPresent) throw CourseNotFoundException()

        val quoteRequests = quoteRequestRepository.findAllByCourseId(course.get().id!!)

        return quoteRequests.map { quoteRequest ->
            QuoteRequestResponseDTO(
                id = quoteRequest.id!!,
                course = CourseResponseDTO(
                    quoteRequest.course.id!!,
                    SemesterResponseDTO(
                        quoteRequest.course.semester.id!!,
                        quoteRequest.course.semester.semester,
                        quoteRequest.course.semester.year,
                        quoteRequest.course.semester.name
                    ),
                    SubjectResponseDTO(quoteRequest.course.subject.id!!, quoteRequest.course.subject.name),
                    quoteRequest.course.number,
                    quoteRequest.course.name,
                    quoteRequest.course.assigned_teachers,
                    quoteRequest.course.current_quotes,
                    quoteRequest.course.total_quotes
                ),
                student = StudentResponseDTO(
                    quoteRequest.student.id!!,
                    quoteRequest.student.firstName,
                    quoteRequest.student.lastName,
                    quoteRequest.student.dni,
                    quoteRequest.student.email,
                    quoteRequest.student.legajo,
                    UserResponseDTO(quoteRequest.student.user.id!!, quoteRequest.student.user.username)
                ),
                state = quoteRequest.state,
                comment = quoteRequest.comment
            )
        }
    }

    private fun decode(code: String): Triple<Long, Long, Int> {
        val ids = code.split("-")
        return Triple(ids[0].toLong(), ids[1].toLong(), ids[2].toInt())
    }
}