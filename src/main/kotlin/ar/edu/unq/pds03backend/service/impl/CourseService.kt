package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.course.CourseRequestDTO
import ar.edu.unq.pds03backend.dto.course.CourseResponseDTO
import ar.edu.unq.pds03backend.exception.CourseNotFoundException
import ar.edu.unq.pds03backend.exception.SemesterNotFoundException
import ar.edu.unq.pds03backend.exception.SubjectNotFoundException
import ar.edu.unq.pds03backend.mapper.CourseMapper
import ar.edu.unq.pds03backend.model.Course
import ar.edu.unq.pds03backend.model.Hour
import ar.edu.unq.pds03backend.model.QuoteState
import ar.edu.unq.pds03backend.repository.ICourseRepository
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.repository.ISubjectRepository
import ar.edu.unq.pds03backend.service.ICourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CourseService(
    @Autowired private val semesterRepository: ISemesterRepository,
    @Autowired private val subjectRepository: ISubjectRepository,
    @Autowired private val courseRepository: ICourseRepository,
    @Autowired private val quoteRequestRepository: IQuoteRequestRepository,
) : ICourseService {
    override fun getById(idCourse: Long): CourseResponseDTO {
        val course = courseRepository.findById(idCourse)
        if (!course.isPresent) throw CourseNotFoundException()
        return mapCourseToDTO(course.get())
    }

    override fun getAllBySemesterAndSubject(idSemester: Long, idSubject: Long): List<CourseResponseDTO> {
        val semester = semesterRepository.findById(idSemester)
        if (!semester.isPresent) throw SemesterNotFoundException()

        val subject = subjectRepository.findById(idSubject)
        if (!subject.isPresent) throw SubjectNotFoundException()

        return courseRepository.findAllBySemesterIdAndSubjectId(semester.get().id!!, subject.get().id!!).map {
            mapCourseToDTO(it)
        }
    }

    @Transactional
    override fun create(idSemester: Long, idSubject: Long, courseRequestDTO: CourseRequestDTO) {
        val semester = semesterRepository.findById(idSemester)
        if (!semester.isPresent) throw SemesterNotFoundException()

        val subject = subjectRepository.findById(idSubject)
        if (!subject.isPresent) throw SubjectNotFoundException()

        courseRepository.save(
            Course(
                semester = semester.get(),
                subject = subject.get(),
                name = courseRequestDTO.name,
                assigned_teachers = courseRequestDTO.assignedTeachers.joinToString(),
                total_quotes = courseRequestDTO.totalQuotes,
                hours = courseRequestDTO.hours.map { Hour(from = it.from, to = it.to, day = it.day) }.toMutableList()
            )
        )
    }

    private fun mapCourseToDTO(course: Course): CourseResponseDTO {
        val requestedQuotes = quoteRequestRepository.countByStateAndCourseId(QuoteState.PENDING, course.id!!)
        val acceptedQuotes = quoteRequestRepository.countByStateAndCourseId(QuoteState.APPROVED, course.id!!)
        return CourseMapper.toDTO(course, requestedQuotes, acceptedQuotes)
    }


}