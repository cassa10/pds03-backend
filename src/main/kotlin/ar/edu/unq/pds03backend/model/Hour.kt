package ar.edu.unq.pds03backend.model

import java.time.DayOfWeek
import javax.persistence.*

@Entity
@Table(name = "hours")
class Hour(
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

        @Column(nullable = false)
    val desde: String,

        @Column(nullable = false)
    val hasta: String,

        @Column(nullable = false)
    val day: Int
)
