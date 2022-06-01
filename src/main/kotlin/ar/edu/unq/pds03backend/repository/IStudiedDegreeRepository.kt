package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.StudiedDegree
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface IStudiedDegreeRepository : JpaRepository<StudiedDegree, Long> {
    fun findByDegreeIdAndStudentId(idDegree: Long, idStudent: Long): Optional<StudiedDegree>
}