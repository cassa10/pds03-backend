package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO.CourseResponseDTO
import ar.edu.unq.pds03backend.model.Course

object CourseResponseDTOMapper {
    fun map(course: Course): CourseResponseDTO {
        with(course) {
            return CourseResponseDTO(
                    idCourse = id!!,
                    name = name
            )
        }
    }
}
