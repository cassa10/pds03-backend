package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
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
        val ids = code.split("-")
        val course = courseRepository.findBySemesterIdAndSubjectIdAndNumber(ids[0].toLong(), ids[1].toLong(), ids[2].toInt())
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

}