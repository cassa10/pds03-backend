package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.CourseRequestDTO

interface ICourseService {
    fun create(idSemester: Long, idSubject: Long, courseRequestDTO: CourseRequestDTO)
}