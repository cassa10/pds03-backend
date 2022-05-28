package ar.edu.unq.pds03backend.model

import ar.edu.unq.pds03backend.utils.SemesterHelper
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.*;

@Entity
@Table(
    name = "semesters",
    uniqueConstraints = [UniqueConstraint(columnNames = ["is_snd_semester", "year"])]
)
class Semester(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "is_snd_semester", nullable = false)
    //True if is second semester of year
    val isSndSemester: Boolean,
    @Column(nullable = false)
    val year: Int,
    @Column(name = "accept_quote_requests_from")
    var acceptQuoteRequestsFrom: LocalDateTime,
    @Column(name = "accept_quote_requests_to")
    var acceptQuoteRequestsTo: LocalDateTime,
) {
    fun isCurrent(): Boolean =
        SemesterHelper.isCurrentYear(year) && SemesterHelper.isCurrentSecondSemester(isSndSemester)

    fun isAcceptQuoteRequestsAvailable(): Boolean {
        val now = LocalDateTime.now()
        return now.isAfter(acceptQuoteRequestsFrom).and(now.isBefore(acceptQuoteRequestsTo))
    }

    fun name(): String = "$year - ${getSemesterName()}"

    private fun getSemesterName(): String = if(isSndSemester){"Segundo Cuatrimestre"}else{"Primer Cuatrimestre"}

    fun hasInvalidAcceptRequestQuotesDates(): Boolean = acceptQuoteRequestsFrom.isAfter(acceptQuoteRequestsTo)

}
