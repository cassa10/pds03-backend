package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.mapper.QuoteRequestMapper
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

    override fun create(quoteRequestRequestDTO: QuoteRequestRequestDTO) {
        val course = courseRepository.findById(quoteRequestRequestDTO.idCourse)
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

    override fun getById(id: Long): QuoteRequestResponseDTO {
        val quoteRequest = quoteRequestRepository.findById(id)

        if (!quoteRequest.isPresent) throw QuoteRequestNotFoundException()

        return QuoteRequestMapper.toDTO(quoteRequest.get())
    }

    override fun getAll(): List<QuoteRequestResponseDTO> {
        val quoteRequests = quoteRequestRepository.findAll()
        return quoteRequests.map { QuoteRequestMapper.toDTO(it) }
    }

    override fun getAllByCourseAndStudent(idCourse: Long, idStudent: Long): List<QuoteRequestResponseDTO> {
        val course = courseRepository.findById(idCourse)
        if (!course.isPresent) throw CourseNotFoundException()

        val student = studentRepository.findById(idStudent)
        if (!student.isPresent) throw StudentNotFoundException()

        val quoteRequests = quoteRequestRepository.findAllByCourseIdAndStudentId(course.get().id!!, student.get().id!!)
        return quoteRequests.map { QuoteRequestMapper.toDTO(it) }
    }

    override fun getAllByCourse(idCourse: Long): List<QuoteRequestResponseDTO> {
        val course = courseRepository.findById(idCourse)
        if (!course.isPresent) throw CourseNotFoundException()

        val quoteRequests = quoteRequestRepository.findAllByCourseId(course.get().id!!)
        return quoteRequests.map { QuoteRequestMapper.toDTO(it) }
    }

    override fun getAllByStudent(idStudent: Long): List<QuoteRequestResponseDTO> {
        val student = studentRepository.findById(idStudent)
        if (!student.isPresent) throw StudentNotFoundException()

        val quoteRequests = quoteRequestRepository.findAllByStudentId(student.get().id!!)
        return quoteRequests.map { QuoteRequestMapper.toDTO(it) }
    }
}