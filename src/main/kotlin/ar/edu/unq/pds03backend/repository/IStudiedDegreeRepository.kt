package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.StudiedDegree
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface IStudiedDegreeRepository : JpaRepository<StudiedDegree, Long> {
    fun findByDegreeIdAndStudentId(idDegree: Long, idStudent: Long): Optional<StudiedDegree>
    fun findAllByStudentId(idStudent: Long): List<StudiedDegree>
    fun findAllByStudentId(idStudent: Long, pageable: Pageable): Page<StudiedDegree>
    fun findAllByDegreeId(idDegree: Long): List<StudiedDegree>
    fun findAllByDegreeId(idDegree: Long, pageable: Pageable): Page<StudiedDegree>
    fun findAllByStudentIdAndDegreeId(idStudent: Long, idDegree: Long): List<StudiedDegree>
    fun findAllByStudentIdAndDegreeId(idStudent: Long, idDegree: Long, pageable: Pageable): Page<StudiedDegree>
}