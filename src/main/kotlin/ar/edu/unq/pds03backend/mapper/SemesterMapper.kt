package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.semester.SemesterResponseDTO
import ar.edu.unq.pds03backend.model.Semester

object SemesterMapper : Mapper<Semester, SemesterResponseDTO> {
    override fun toDTO(semester: Semester): SemesterResponseDTO =
        SemesterResponseDTO(
            id = semester.id!!,
            isSndSemester = semester.isSndSemester,
            year = semester.year,
            name = semester.name(),
            acceptQuoteRequestsFrom = semester.acceptQuoteRequestsFrom,
            acceptQuoteRequestsTo = semester.acceptQuoteRequestsTo,
            isCurrentSemester = semester.isCurrent(),
            isAcceptQuoteRequestsAvailable = semester.isAcceptQuoteRequestsAvailable(),
        )
}