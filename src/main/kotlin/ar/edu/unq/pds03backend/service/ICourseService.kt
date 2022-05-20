package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.course.CourseRequestDTO
import ar.edu.unq.pds03backend.dto.course.CourseResponseDTO

interface ICourseService {
    fun getById(idCourse: Long): CourseResponseDTO
    fun getAllBySemesterAndSubject(idSemester: Long, idSubject: Long): List<CourseResponseDTO>
    fun create(idSemester: Long, idSubject: Long, courseRequestDTO: CourseRequestDTO)
}