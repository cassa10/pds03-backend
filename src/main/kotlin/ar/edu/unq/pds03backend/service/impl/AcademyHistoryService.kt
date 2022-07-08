package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.academyHistory.StudiedDegreeDTO
import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO
import ar.edu.unq.pds03backend.mapper.StudiedDegreeMapper
import ar.edu.unq.pds03backend.model.*
import ar.edu.unq.pds03backend.repository.*
import ar.edu.unq.pds03backend.service.IAcademyHistoryService
import ar.edu.unq.pds03backend.utils.StatusStudiedCourseHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class AcademyHistoryService(
    @Autowired private val degreeRepository: IDegreeRepository,
    @Autowired private val studentRepository: IStudentRepository,
    @Autowired private val studiedDegreeRepository: IStudiedDegreeRepository,
    @Autowired private val subjectRepository: ISubjectRepository,
    @Autowired private val studiedSubjectRepository: IStudiedSubjectRepository
) : IAcademyHistoryService {

    override fun updateAcademyHistory(data: List<CsvAcademyHistoryRequestDTO>) {
        val dataGroupByStudentAndDegree = data.groupBy { Pair(it.dni.substring(4), it.codigoCarrera) }

        dataGroupByStudentAndDegree.forEach {
            val maybeDegree = degreeRepository.findByGuaraniCode(it.key.second)
            val maybeStudent = studentRepository.findByDni(it.key.first)
            if (!maybeStudent.isPresent) return@forEach

            var maybeStudiedDegree =
                studiedDegreeRepository.findByDegreeIdAndStudentId(maybeDegree.get().id!!, maybeStudent.get().id!!)
            if (!maybeStudiedDegree.isPresent) return@forEach

            importStudiedSubjects(it.value, maybeStudiedDegree.get())
        }
    }

    //TODO: Contemplar que el alumno ya tenga materias cursadas y agregar solo las que no estén registradas
    private fun importStudiedSubjects(data: List<CsvAcademyHistoryRequestDTO>, studiedDegree: StudiedDegree) {
        data.forEach {
            val maybeSubject = subjectRepository.findByGuaraniCode(it.codigoMateria)
            if (!maybeSubject.isPresent) return@forEach

            val status: StatusStudiedCourse =
                StatusStudiedCourseHelper.parseResultColumnAcademyHistoryFile(it.resultado)

            studiedSubjectRepository.save(
                StudiedSubject(
                    subject = maybeSubject.get(),
                    mark = it.nota.toIntOrNull(),
                    status = status,
                    studiedDegree = studiedDegree
                )
            )
        }
    }

    override fun getAllStudiedDegrees(): List<StudiedDegreeDTO> =
        studiedDegreeRepository.findAll().map { StudiedDegreeMapper.toDTO(it) }

    override fun getAllStudiedDegrees(pageable: Pageable): Page<StudiedDegreeDTO> =
        studiedDegreeRepository.findAll(pageable).map(StudiedDegreeMapper::toDTO)

    override fun getAllStudiedDegreesByStudent(idStudent: Long): List<StudiedDegreeDTO> =
        studiedDegreeRepository.findAllByStudentId(idStudent).map { StudiedDegreeMapper.toDTO(it) }

    override fun getAllStudiedDegreesByStudent(idStudent: Long, pageable: Pageable): Page<StudiedDegreeDTO> =
        studiedDegreeRepository.findAllByStudentId(idStudent, pageable).map(StudiedDegreeMapper::toDTO)

    override fun getAllStudiedDegreesByDegree(idDegree: Long): List<StudiedDegreeDTO> =
        studiedDegreeRepository.findAllByDegreeId(idDegree).map { StudiedDegreeMapper.toDTO(it) }

    override fun getAllStudiedDegreesByDegree(idDegree: Long, pageable: Pageable): Page<StudiedDegreeDTO> =
        studiedDegreeRepository.findAllByDegreeId(idDegree, pageable).map(StudiedDegreeMapper::toDTO)

    override fun getAllStudiedDegreesByStudentAndDegree(idStudent: Long, idDegree: Long): List<StudiedDegreeDTO> =
        studiedDegreeRepository.findAllByStudentIdAndDegreeId(idStudent, idDegree).map { StudiedDegreeMapper.toDTO(it) }

    override fun getAllStudiedDegreesByStudentAndDegree(
        idStudent: Long,
        idDegree: Long,
        pageable: Pageable
    ): Page<StudiedDegreeDTO> =
        studiedDegreeRepository.findAllByStudentIdAndDegreeId(idStudent, idDegree, pageable)
            .map(StudiedDegreeMapper::toDTO)
}