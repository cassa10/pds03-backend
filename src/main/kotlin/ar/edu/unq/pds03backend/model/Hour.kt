package ar.edu.unq.pds03backend.model

import java.time.DayOfWeek
import javax.persistence.*

@Entity
@Table(name = "hours")
class Hour(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "_from", nullable = false)
    val from: String,

    @Column(name = "_to", nullable = false)
    val to: String,

    @Column(nullable = false)
    val day: DayOfWeek
)
