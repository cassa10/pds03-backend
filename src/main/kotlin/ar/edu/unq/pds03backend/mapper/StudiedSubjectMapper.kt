package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.academyHistory.StudiedSubjectDTO
import ar.edu.unq.pds03backend.model.StudiedSubject

object StudiedSubjectMapper : Mapper<StudiedSubject, StudiedSubjectDTO> {
    override fun toDTO(studiedSubject: StudiedSubject): StudiedSubjectDTO =
        StudiedSubjectDTO(
            id = studiedSubject.id!!,
            subject = SubjectMapper.toSimpleDTO(studiedSubject.subject),
            mark = studiedSubject.mark,
            status = studiedSubject.status.translate(),
            passed = studiedSubject.passed(),
            inProgress = studiedSubject.inProgress()
        )
}