package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@DiscriminatorValue("1")
class Student(
    id: Long?,
    firstName: String,
    lastName: String,
    dni: String,
    email: String,
    username: String,

    @Column(unique = true)
    val legajo: String,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinTable(
        name = "student_enrolled_degree",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "degree_id", referencedColumnName = "id")],
    )
    val enrolledDegrees: Collection<Degree>,

    @OneToMany(mappedBy = "student")
    val degree_histories: Collection<StudiedDegree>,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinTable(
        name = "student_enrolled_course",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "course_id", referencedColumnName = "id")],
    )
    //Comisiones ya inscriptas
    //TODO: cuando se apruebe una solicitud de cupo agregar a esta collecion la comision
    val enrolledCourses: MutableCollection<Course>
) : User(id, firstName, lastName, dni, email, username) {
    override fun isStudent(): Boolean = true

    fun passed(subject: Subject): Boolean =
        degree_histories.any { studiedDegree -> studiedDegree.studied_subjects.any { it.subject == subject && it.passed() } }

    //TODO: Revisar si sigue aplicando utilizar "studiedSubject.inProgress()" si ahora existe la lista enrolledSubjects
    fun studiedOrEnrolled(subject: Subject): Boolean =
        isEnrolled(subject) || degree_histories.any { studiedDegree ->
            studiedDegree.studied_subjects.any { it.subject == subject && it.inProgress() }
        }

    fun isEnrolled(subject: Subject) = enrolledCourses.any { it.subject == subject }

    fun isStudingAnyDegree(degrees: Collection<Degree>): Boolean =
        enrolledDegrees.any { enrolledDegree -> degrees.any { enrolledDegree.id!! == it.id!! } }

    fun addEnrolledCourse(course: Course) = enrolledCourses.add(course)
    fun deleteEnrolledCourse(course: Course) = enrolledCourses.remove(course)

    fun anyCoefficientIsGreaterThan(number: Float) = degree_histories.any {it.coefficient >= number}

    data class Builder(
        var id: Long? = null,
        var firstName: String = "",
        var lastName: String = "",
        var dni: String = "",
        var email: String = "",
        var username: String = "",
        var legajo: String = "",
        var enrolledDegrees: Collection<Degree> = listOf(),
        var degree_histories: Collection<StudiedDegree> = listOf(),
        var enrolledCourses: MutableCollection<Course> = mutableListOf(),
    ) {

        fun build(): Student = Student(id, firstName,lastName, dni, email, username, legajo, enrolledDegrees, degree_histories, enrolledCourses)
        fun withDni(dni: String) = apply {this.dni = dni}
        fun withLegajo(legajo: String) = apply {this.legajo = legajo}
    }
}