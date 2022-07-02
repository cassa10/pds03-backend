package ar.edu.unq.pds03backend.mapper

import ar.edu.unq.pds03backend.dto.academyHistory.StudiedDegreeDTO
import ar.edu.unq.pds03backend.dto.degree.SimpleDegreeResponseDTO
import ar.edu.unq.pds03backend.model.StudiedDegree

object StudiedDegreeMapper : Mapper<StudiedDegree, StudiedDegreeDTO> {
    override fun toDTO(studiedDegree: StudiedDegree): StudiedDegreeDTO =
        StudiedDegreeDTO(
            id = studiedDegree.id!!,
            coefficient = studiedDegree.coefficient,
            degree = SimpleDegreeResponseDTO(studiedDegree.degree.id!!, studiedDegree.degree.name, studiedDegree.degree.acronym),
            student = StudentMapper.toSimpleStudentResponseDTO(studiedDegree.student),
            studiedSubjects = studiedDegree.studied_subjects.map { StudiedSubjectMapper.toDTO(it) },
            registryState = studiedDegree.registryState,
            plan = studiedDegree.plan,
            quality = studiedDegree.quality,
            isRegular = studiedDegree.isRegular,
            location = studiedDegree.location,
        )
}