package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.course.CourseRequestDTO
import ar.edu.unq.pds03backend.exception.SemesterNotFoundException
import ar.edu.unq.pds03backend.exception.SubjectNotFoundException
import ar.edu.unq.pds03backend.model.Course
import ar.edu.unq.pds03backend.model.Hour
import ar.edu.unq.pds03backend.repository.ICourseRepository
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.repository.ISubjectRepository
import ar.edu.unq.pds03backend.service.ICourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CourseService(
        @Autowired private val semesterRepository: ISemesterRepository,
        @Autowired private val subjectRepository: ISubjectRepository,
        @Autowired private val courseRepository: ICourseRepository,
) : ICourseService {

    override fun create(idSemester: Long, idSubject: Long, courseRequestDTO: CourseRequestDTO) {
        val semester = semesterRepository.findById(idSemester)
        if (!semesterRepository.findById(idSemester).isPresent) throw SemesterNotFoundException()

        val subject = subjectRepository.findById(idSubject)
        if (!subject.isPresent) throw SubjectNotFoundException()

        courseRepository.save(
                Course(
                        semester = semester.get(),
                        subject = subject.get(),
                        name = courseRequestDTO.name,
                        number = 1,
                        assigned_teachers = courseRequestDTO.assignedTeachers.joinToString(),
                        total_quotes = courseRequestDTO.totalQuotes,
                        hours = courseRequestDTO.hours.map { Hour(desde = it.from, hasta = it.to, day = it.day.value) }.toMutableList()
                )
        )
    }
}