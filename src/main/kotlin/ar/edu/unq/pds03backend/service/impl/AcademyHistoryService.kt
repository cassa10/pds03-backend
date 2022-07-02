package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.academyHistory.StudiedDegreeDTO
import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO
import ar.edu.unq.pds03backend.exception.StudiedDegreeNotFoundException
import ar.edu.unq.pds03backend.mapper.StudiedDegreeMapper
import ar.edu.unq.pds03backend.model.*
import ar.edu.unq.pds03backend.repository.*
import ar.edu.unq.pds03backend.service.IAcademyHistoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

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
        dataGroupByStudentAndDegree.forEach { it ->
            val dni = it.key.first
            val codigoCarrera = it.key.second
            val data = it.value

            val coeficiente =
                data.filter { it.resultado != StatusStudiedCourse.IN_PROGRESS.toValueOfAcademyHistoriesFile() }
                    .map { it.nota.toInt() }.average().toFloat()
            val (maybeDegree, maybeStudent) = importStudiedDegree(codigoCarrera, dni, coeficiente)

            importStudiedSubjects(data, maybeDegree, maybeStudent)
        }
    }

    private fun importStudiedDegree(
        codigoCarrera: String,
        dni: String,
        coeficiente: Float
    ): Pair<Optional<Degree>, Optional<Student>> {
        val maybeDegree = degreeRepository.findByGuaraniCode(codigoCarrera)
//        val maybeStudent = studentRepository.findByDni(dni)
        val maybeStudent = studentRepository.findByDni("11111111")
        //TODO: Crear usuario en caso de que no exista

        var maybeStudiedDegree =
            studiedDegreeRepository.findByDegreeIdAndStudentId(maybeDegree.get().id!!, maybeStudent.get().id!!)
        if (!maybeStudiedDegree.isPresent) throw StudiedDegreeNotFoundException()
        //TODO: Contemplar el caso de que ya haya información del alumno para la carrera. No importar todas las materias, agregar solo las que no estén registradas

        return Pair(maybeDegree, maybeStudent)
    }

    private fun importStudiedSubjects(
        data: List<CsvAcademyHistoryRequestDTO>,
        maybeDegree: Optional<Degree>,
        maybeStudent: Optional<Student>
    ) {
        data.forEach { it ->
            val maybeSubject = subjectRepository.findByGuaraniCode(it.codigoMateria)
            val maybeStudiedDegree =
                studiedDegreeRepository.findByDegreeIdAndStudentId(maybeDegree.get().id!!, maybeStudent.get().id!!)
            val status: StatusStudiedCourse = when (it.resultado) {
                StatusStudiedCourse.IN_PROGRESS.toValueOfAcademyHistoriesFile() -> StatusStudiedCourse.IN_PROGRESS
                StatusStudiedCourse.APPROVAL.toValueOfAcademyHistoriesFile() -> StatusStudiedCourse.APPROVAL
                StatusStudiedCourse.PROMOTED.toValueOfAcademyHistoriesFile() -> StatusStudiedCourse.PROMOTED
                else -> StatusStudiedCourse.PENDING_APPROVAL
            }
            studiedSubjectRepository.save(
                StudiedSubject(
                    null, maybeSubject.get(), it.nota.toInt(), status, maybeStudiedDegree.get()
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

    override fun getAllStudiedDegreesByDegree(idDegree: Long, pageable: Pageable): Page<StudiedDegreeDTO>  =
            studiedDegreeRepository.findAllByDegreeId(idDegree, pageable).map(StudiedDegreeMapper::toDTO)

    override fun getAllStudiedDegreesByStudentAndDegree(idStudent: Long, idDegree: Long): List<StudiedDegreeDTO> =
        studiedDegreeRepository.findAllByStudentIdAndDegreeId(idStudent, idDegree).map { StudiedDegreeMapper.toDTO(it) }

    override fun getAllStudiedDegreesByStudentAndDegree(idStudent: Long, idDegree: Long, pageable: Pageable): Page<StudiedDegreeDTO> =
            studiedDegreeRepository.findAllByStudentIdAndDegreeId(idStudent, idDegree, pageable).map(StudiedDegreeMapper::toDTO)
}