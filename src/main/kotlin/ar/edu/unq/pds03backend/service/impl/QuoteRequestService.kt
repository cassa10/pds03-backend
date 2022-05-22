package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.AdminCommentRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesInfoResponseDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.mapper.QuoteRequestMapper
import ar.edu.unq.pds03backend.mapper.QuoteRequestSubjectPendingMapper
import ar.edu.unq.pds03backend.model.QuoteRequest
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.model.Semester
import ar.edu.unq.pds03backend.repository.ICourseRepository
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.repository.IStudentRepository
import ar.edu.unq.pds03backend.service.IQuoteRequestService
import ar.edu.unq.pds03backend.utils.SemesterHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class QuoteRequestService(
    @Autowired private val quoteRequestRepository: IQuoteRequestRepository,
    @Autowired private val courseRepository: ICourseRepository,
    @Autowired private val studentRepository: IStudentRepository,
    @Autowired private val semesterRepository: ISemesterRepository,
) : IQuoteRequestService {

    @Transactional
    override fun create(quoteRequestRequestDTO: QuoteRequestRequestDTO) {
        val courses = courseRepository.findAllById(quoteRequestRequestDTO.idCourses)
        if (courses.isEmpty()) throw CourseNotFoundException()

        val student = studentRepository.findById(quoteRequestRequestDTO.idStudent)
        if (!student.isPresent) throw StudentNotFoundException()

        if (courses.any{student.get().studiedOrEnrolled(it.subject)}) throw StudentHasAlreadyEnrolledSubject()

        //If quoteRequest was already created by that student, there are skipped
        courses.forEach {
            val quoteRequest = quoteRequestRepository.findByCourseIdAndStudentId(it.id!!, student.get().id!!)
            if (!quoteRequest.isPresent) {
                quoteRequestRepository.save(
                    QuoteRequest(
                        course = it,
                        student = student.get(),
                        state = QuoteState.PENDING,
                        comment = quoteRequestRequestDTO.comment
                    )
                )
            }
        }

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

    override fun getQuoteRequestSubjectsPending(): List<QuoteRequestSubjectPendingResponseDTO> {
        val semester = getCurrentSemester()
        val quoteRequestSubjectsPending =
            quoteRequestRepository.findAllByStateAndCourseSemesterId(QuoteState.PENDING, semester.id!!)

        // TODO: refactor in query
        val list = mutableListOf<Long>()
        return quoteRequestSubjectsPending.filter {
            !list.contains(it.course.id).apply {
                list.add(it.course.id!!)
            }
        }.map {
            QuoteRequestSubjectPendingMapper(quoteRequestRepository).map(it)
        }
    }

    @Transactional
    override fun addAdminComment(idQuoteRequest: Long, adminCommentRequestDTO: AdminCommentRequestDTO) {
        val quoteRequest = quoteRequestRepository.findById(idQuoteRequest)

        if (!quoteRequest.isPresent) throw QuoteRequestNotFoundException()

        val newQuoteRequest = quoteRequest.get()
        newQuoteRequest.adminComment = adminCommentRequestDTO.comment

        quoteRequestRepository.save(newQuoteRequest)
    }

    override fun findAllStudentsWithQuoteStatusPendingCurrentSemester(): List<StudentWithQuotesInfoResponseDTO> {
        val semester = getCurrentSemester()
        val studentsWithQuoteRequests = quoteRequestRepository.findAllStudentsWithQuoteRequestStateAndCourseSemesterId(QuoteState.PENDING, semester.id!!)
        return studentsWithQuoteRequests.map{
            val numberOfPendingQuoteRequest = quoteRequestRepository.countByStateAndStudentIdAndCourseSemesterId(QuoteState.PENDING, it.id!!, semester.id!!)
            StudentWithQuotesInfoResponseDTO.Mapper(it, numberOfPendingQuoteRequest).map()
        }
    }

    private fun getCurrentSemester(): Semester {
        return getSemester(SemesterHelper.currentYear, SemesterHelper.currentIsSecondSemester)
    }

    private fun getSemester(year: Int, isSndSemester: Boolean): Semester {
        val maybeSemester = semesterRepository.findByYearAndIsSndSemester(year, isSndSemester)
        if(!maybeSemester.isPresent) throw SemesterNotFoundException()
        return maybeSemester.get()
    }
}
