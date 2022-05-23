package ar.edu.unq.pds03backend.model

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

        // TODO: pasarlo a un one to many
        @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
        var hours: MutableCollection<Hour>
)
{
        fun isCurrent(): Boolean = semester.isCurrent()

        fun belongsToDegree(degree: Degree): Boolean = subject.degrees.contains(degree)
}
