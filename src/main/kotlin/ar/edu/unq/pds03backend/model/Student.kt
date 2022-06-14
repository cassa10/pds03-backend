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
    val enrolledCourses: MutableCollection<Course>
) : User(id, firstName, lastName, dni, email, username) {
    override fun isStudent(): Boolean = true

    fun isStudyingOrEnrolled(subject: Subject): Boolean =
        isEnrolled(subject) || degree_histories.any { studiedDegree ->
            studiedDegree.studied_subjects.any { it.subject == subject && it.inProgress() }
        }

    fun isEnrolled(subject: Subject) = enrolledCourses.any { it.subject == subject }

    fun isStudyingAnyDegree(degrees: Collection<Degree>): Boolean =
        enrolledDegrees.any { enrolledDegree -> degrees.any { enrolledDegree.id!! == it.id!! } }

    fun addEnrolledCourse(course: Course) = enrolledCourses.add(course)
    fun deleteEnrolledCourse(course: Course) = enrolledCourses.remove(course)

    fun anyCoefficientIsGreaterThan(number: Float) = degree_histories.any {it.coefficient >= number}

    fun maxCoefficient(): Float {
        if (degree_histories.isEmpty()) return 0f
        return degree_histories.maxOf { it.coefficient }
    }

    fun getStudiedDegreeCoefficient(degree: Degree): Float = degree_histories.find { it.degree == degree }?.coefficient ?: 0f

    fun getPassedSubjects(): List<Subject> {
        val result: MutableList<Subject> = mutableListOf()
        degree_histories.forEach { studiedDegree ->
            result.addAll(studiedDegree.studied_subjects.filter{ it.passed() }.map{ it.subject })
        }
        return result
    }

    fun canCourseSubject(subject: Subject): Boolean = !isStudyingOrEnrolled(subject)

    fun passedAllPrerequisiteSubjects(subject: Subject): Boolean {
        val allPassedSubjects: List<Subject> = getPassedSubjects()
        return subject.prerequisiteSubjects.all { allPassedSubjects.contains(it) }
    }

    fun canCourseSubjectWithPrerequisiteSubjects(subject: Subject): Boolean {
        return canCourseSubject(subject) && passedAllPrerequisiteSubjects(subject)
    }


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