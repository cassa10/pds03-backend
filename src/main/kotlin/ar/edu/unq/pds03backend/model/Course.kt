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
        val name: String,

        @Column(nullable = false)
        val assigned_teachers: String,

        @Column(nullable = false)
        val total_quotes: Int,
        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable(
                name = "course_student",
                joinColumns = [JoinColumn(name = "course_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")]
        )
        val students: Collection<Student>? = null,

        @OneToMany(cascade = [CascadeType.ALL])
        val hours: MutableCollection<Hour>
)