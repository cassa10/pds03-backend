package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(
    name = "courses",
    uniqueConstraints = [UniqueConstraint(columnNames = ["semester_id", "subject_id", "number"])]
)
class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne
    @JoinColumn(name="semester_id")
    val semester: Semester,

    @ManyToOne
    @JoinColumn(name="subject_id")
    val subject: Subject,

    @Column(unique = true, nullable = false)
    val number: Int,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val assigned_teachers: String,

    @Column(nullable = false)
    val current_quotes: Int,

    @Column(nullable = false)
    val total_quotes: Int,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "course_student",
        joinColumns = [JoinColumn(name = "course_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")]
    )
    val students_enrolled: Collection<Student>
)
