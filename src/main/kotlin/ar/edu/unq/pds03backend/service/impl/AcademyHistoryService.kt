package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.csv.CsvAcademyHistoryRequestDTO
import ar.edu.unq.pds03backend.model.*
import ar.edu.unq.pds03backend.repository.*
import ar.edu.unq.pds03backend.service.IAcademyHistoryService
import org.springframework.beans.factory.annotation.Autowired
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
        val dataGroupByStudentAndDegree = data.groupBy { Pair(it.legajo, it.carrera) }
        dataGroupByStudentAndDegree.forEach { it ->
            val legajo = it.key.first
            val carrera = it.key.second
            val data = it.value

            val coeficiente = data.filter { it.resultado != "En Curso" }.map { it.nota }.average().toFloat()
            val (maybeDegree, maybeStudent) = importStudiedDegree(carrera, legajo, coeficiente)

            importStudiedSubjects(data, maybeDegree, maybeStudent)
        }
    }

    private fun importStudiedDegree(carrera: String, legajo: String, coeficiente: Float): Pair<Optional<Degree>, Optional<Student>> {
        val maybeDegree = degreeRepository.findByName(carrera)
        val maybeStudent = studentRepository.findByLegajo(legajo)
        //TODO: Crear usuario en caso de que no exista

        var maybeStudiedDegree =
            studiedDegreeRepository.findByDegreeIdAndStudentId(maybeDegree.get().id!!, maybeStudent.get().id!!)
        if (!maybeStudiedDegree.isPresent) {
            studiedDegreeRepository.save(
                StudiedDegree(
                    null, maybeDegree.get(), maybeStudent.get(), coeficiente, mutableListOf()
                )
            )
        }
        //TODO: Contemplar el caso de que ya haya información del alumno para la carrera. No importar todas las materias, agregar solo las que no estén registradas

        return Pair(maybeDegree, maybeStudent)
    }

    private fun importStudiedSubjects(
        data: List<CsvAcademyHistoryRequestDTO>,
        maybeDegree: Optional<Degree>,
        maybeStudent: Optional<Student>
    ) {
        data.forEach { it ->
            val maybeSubject = subjectRepository.findByName(it.materia)
            val maybeStudiedDegree =
                studiedDegreeRepository.findByDegreeIdAndStudentId(maybeDegree.get().id!!, maybeStudent.get().id!!)
            val status: StatusStudiedCourse = when (it.resultado) {
                "En Curso" -> StatusStudiedCourse.IN_PROGRESS
                "Aprobado" -> StatusStudiedCourse.APPROVAL
                "Promocionado" -> StatusStudiedCourse.PROMOTED
                else -> StatusStudiedCourse.PENDING_APPROVAL
            }
            studiedSubjectRepository.save(
                StudiedSubject(
                    null, maybeSubject.get(), it.nota, status, maybeStudiedDegree.get()
                )
            )
        }
    }
}