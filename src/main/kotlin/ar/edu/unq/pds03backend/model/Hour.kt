package ar.edu.unq.pds03backend.model

import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@Table(name = "hours")
class Hour(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "_from", nullable = false)
    val from: LocalTime,

    @Column(name = "_to", nullable = false)
    val to: LocalTime,

    @Column(name = "_day", nullable = false)
    val day: DayOfWeek
) {
    fun getFromString(): String = from.format(DateTimeFormatter.ofPattern("HH:mm"))
    fun getToString(): String = to.format(DateTimeFormatter.ofPattern("HH:mm"))
    fun isInvalidHour(): Boolean = from.isAfter(to)
}
