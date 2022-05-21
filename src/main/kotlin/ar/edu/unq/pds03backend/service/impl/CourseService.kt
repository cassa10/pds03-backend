package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.course.CourseRequestDTO
import ar.edu.unq.pds03backend.dto.course.CourseResponseDTO
import ar.edu.unq.pds03backend.dto.course.CourseUpdateRequestDTO
import ar.edu.unq.pds03backend.dto.course.HourRequestDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.mapper.CourseMapper
import ar.edu.unq.pds03backend.model.*
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
        return mapCourseToDTO(getCourse(idCourse))
    }

    override fun getAllBySemesterAndSubject(idSemester: Long, idSubject: Long): List<CourseResponseDTO> {
        val (semester, subject) = getSemesterAndSubject(idSemester, idSubject)
        return courseRepository.findAllBySemesterIdAndSubjectId(semester.id!!, subject.id!!).map {
            mapCourseToDTO(it)
        }
    }

    @Transactional
    override fun create(idSemester: Long, idSubject: Long, courseRequestDTO: CourseRequestDTO) {
        val (semester, subject) = getSemesterAndSubject(idSemester, idSubject)
        val hours = mapHours(courseRequestDTO.hours)
        courseRepository.save(
            Course(
                semester = semester,
                subject = subject,
                name = courseRequestDTO.name,
                assigned_teachers = courseRequestDTO.assignedTeachers.joinToString(),
                total_quotes = courseRequestDTO.totalQuotes,
                hours = hours,
            )
        )
    }

    @Transactional
    override fun update(idCourse: Long, courseUpdateRequestDTO: CourseUpdateRequestDTO) {
        val course = getCourse(idCourse)
        if (courseUpdateRequestDTO.totalQuotes < course.total_quotes) throw CannotUpdateCourseInvalidTotalQuotesException()
        course.total_quotes = courseUpdateRequestDTO.totalQuotes
        course.hours = mapHours(courseUpdateRequestDTO.hours)
        course.name = courseUpdateRequestDTO.name
        course.assigned_teachers = courseUpdateRequestDTO.assignedTeachers.joinToString()
        courseRepository.save(course)
    }

    @Transactional
    override fun delete(idCourse: Long) {
        getCourse(idCourse)
        val numberOfQuoteReqs = quoteRequestRepository.countByCourseId(idCourse)
        if (numberOfQuoteReqs > 0) throw CannotDeleteCourseException()
        courseRepository.deleteById(idCourse)
    }

    private fun mapHours(hoursDTO: List<HourRequestDTO>): MutableCollection<Hour> {
        val hours = hoursDTO.map { Hour(from = it.getFromAsLocalTime(), to = it.getToAsLocalTime(), day = it.day) }.toMutableList()
        if(hours.any { it.isInvalidHour() }) throw InvalidRequestHours()
        return hours
    }


    private fun getCourse(idCourse: Long):Course {
        val course = courseRepository.findById(idCourse)
        if (!course.isPresent) throw CourseNotFoundException()
        return course.get()
    }

    private fun getSemesterAndSubject(idSemester: Long, idSubject: Long): Pair<Semester, Subject> {
        val semester = semesterRepository.findById(idSemester)
        if (!semester.isPresent) throw SemesterNotFoundException()

        val subject = subjectRepository.findById(idSubject)
        if (!subject.isPresent) throw SubjectNotFoundException()

        return semester.get() to subject.get()
    }
    private fun mapCourseToDTO(course: Course): CourseResponseDTO {
        val requestedQuotes = quoteRequestRepository.countByStateAndCourseId(QuoteState.PENDING, course.id!!)
        val acceptedQuotes = quoteRequestRepository.countByStateAndCourseId(QuoteState.APPROVED, course.id!!)
        return CourseMapper.toDTO(course, requestedQuotes, acceptedQuotes)
    }


}