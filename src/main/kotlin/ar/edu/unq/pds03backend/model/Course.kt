package ar.edu.unq.pds03backend.model

import java.time.LocalDateTime
import javax.persistence.*;

@Entity
@Table(name = "courses")
class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "semester_id")
    val semester: Semester,

    @ManyToOne
    @JoinColumn(name = "subject_id")
    val subject: Subject,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var assigned_teachers: String,

    @Column(nullable = false)
    var total_quotes: Int,

    @OneToMany(cascade = [CascadeType.ALL])
    var hours: MutableCollection<Hour>,

    @Column(nullable = false)
    var location: String,

    @Column(name = "external_id", nullable = false)
    var externalId: String,
) {
    fun isCurrent(): Boolean = semester.isCurrent()

    fun belongsToDegree(degree: Degree): Boolean = subject.degrees.contains(degree)

    override fun equals(other: Any?): Boolean = (other is Course) && id == other.id

    data class Builder(
        private var id: Long? = null,
        private var semester: Semester = Semester(1, false, 2000, LocalDateTime.MIN, LocalDateTime.MAX),
        private var subject: Subject = Subject.Builder().build(),
        private var name: String = "",
        private var assigned_teachers: String = "",
        private var total_quotes: Int = 0,
        private var hours: MutableCollection<Hour> = mutableListOf(),
        private var location: String = "",
        private var externalId: String = ""
    ) {
        fun build(): Course =
            Course(
                id = id, semester = semester, subject = subject, name = name,
                assigned_teachers = assigned_teachers, total_quotes = total_quotes,
                hours = hours, location = location, externalId = externalId
            )
        fun withId(id:Long) = apply { this.id = id }
        fun withSubject(subject: Subject) = apply { this.subject = subject }
        fun withSemester(semester: Semester) = apply { this.semester = semester }
    }
}
