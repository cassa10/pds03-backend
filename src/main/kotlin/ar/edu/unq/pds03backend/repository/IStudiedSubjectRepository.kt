package ar.edu.unq.pds03backend.repository

import ar.edu.unq.pds03backend.model.StudiedSubject
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface IStudiedSubjectRepository : JpaRepository<StudiedSubject, Long> {
    fun findBySubjectIdAndStudiedDegreeId(idSubject: Long, idStudiedDegree: Long): Optional<StudiedSubject>
}