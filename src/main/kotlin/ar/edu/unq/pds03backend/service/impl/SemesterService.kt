package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.semester.SemesterRequestDTO
import ar.edu.unq.pds03backend.dto.semester.SemesterResponseDTO
import ar.edu.unq.pds03backend.dto.semester.UpdateSemesterRequestDTO
import ar.edu.unq.pds03backend.exception.InvalidRequestAcceptRequestQuotesDates
import ar.edu.unq.pds03backend.exception.SemesterAlreadyExistException
import ar.edu.unq.pds03backend.exception.SemesterNotFoundException
import ar.edu.unq.pds03backend.mapper.SemesterMapper
import ar.edu.unq.pds03backend.model.Semester
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.service.ISemesterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SemesterService(@Autowired val semesterRepository: ISemesterRepository) : ISemesterService {
    override fun getSemesterById(idSemester: Long): SemesterResponseDTO {
        return SemesterMapper.toDTO(findSemester(idSemester))
    }

    override fun getSemesterByYearAndIsSndSemester(year: Int, isSndSemester: Boolean): SemesterResponseDTO {
        val maybeSemester = semesterRepository.findByYearAndIsSndSemester(year, isSndSemester)
        if (!maybeSemester.isPresent) throw SemesterNotFoundException()
        return SemesterMapper.toDTO(maybeSemester.get())
    }

    override fun getAll(): List<SemesterResponseDTO> {
        return semesterRepository.findAll().map{ SemesterMapper.toDTO(it) }
    }

    override fun getAll(pageable: Pageable): Page<SemesterResponseDTO> {
        return semesterRepository.findAll(pageable).map{ SemesterMapper.toDTO(it) }
    }

    @Transactional
    override fun createSemester(semesterRequestDTO: SemesterRequestDTO): SemesterResponseDTO {
        val maybeSemester = semesterRepository.findByYearAndIsSndSemester(semesterRequestDTO.year, semesterRequestDTO.isSndSemester)
        if (maybeSemester.isPresent) throw SemesterAlreadyExistException()
        val semester = semesterRequestDTO.mapToSemester()
        return SemesterMapper.toDTO(validateSemesterAndSave(semester))
    }

    @Transactional
    override fun updateSemester(idSemester: Long, updateSemesterReqDTO: UpdateSemesterRequestDTO) {
        val semester = findSemester(idSemester)
        semester.acceptQuoteRequestsFrom = updateSemesterReqDTO.acceptQuoteRequestsFrom
        semester.acceptQuoteRequestsTo = updateSemesterReqDTO.acceptQuoteRequestsTo
        validateSemesterAndSave(semester)
    }

    private fun findSemester(idSemester: Long): Semester {
        val maybeSemester = semesterRepository.findById(idSemester)
        if (!maybeSemester.isPresent) throw SemesterNotFoundException()
        return maybeSemester.get()
    }

    private fun validateSemesterAndSave(semester: Semester):Semester {
        if(semester.hasInvalidAcceptRequestQuotesDates()) throw InvalidRequestAcceptRequestQuotesDates()
        return semesterRepository.save(semester)
    }
}