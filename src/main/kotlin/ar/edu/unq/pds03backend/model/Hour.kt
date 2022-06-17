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
    fun String(): String = "{'day': $day, 'from': $from, 'to': $to}"
    fun isInvalidHour(): Boolean = from.isAfter(to)
    fun intercept(hour: Hour): Boolean = day == hour.day && !(to.isBefore(hour.from) || from.isAfter(hour.to))
    fun anyIntercept(hours: List<Hour>): Boolean = hours.any { this.intercept(it) }

    data class Builder(
        var id: Long? = null,
        var day: DayOfWeek = DayOfWeek.MONDAY,
        var from: LocalTime = LocalTime.now(),
        var to: LocalTime = LocalTime.now(),
    ) {
        fun build(): Hour = Hour(id = id, day = day, from = from, to = to)
        fun withId(id: Long) = apply { this.id = id }
        fun withDay(day: DayOfWeek) = apply { this.day = day }
        fun withFrom(from: LocalTime) = apply { this.from = from }
        fun withTo(to: LocalTime) = apply { this.to = to }
    }
}
