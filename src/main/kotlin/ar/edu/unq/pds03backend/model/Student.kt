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
    password: String,

    val legajo: String,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinTable(
        name = "student_enrolled_degree",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "degree_id", referencedColumnName = "id")],
    )
    val enrolledDegrees: MutableCollection<Degree>,

    @OneToMany(mappedBy = "student")
    val degreeHistories: MutableCollection<StudiedDegree>,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinTable(
        name = "student_enrolled_course",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "course_id", referencedColumnName = "id")],
    )
    //Comisiones ya inscriptas
    val enrolledCourses: MutableCollection<Course>
) : User(id=id,firstName=firstName,lastName=lastName,dni=dni,password=password,email=email) {
    override fun isStudent(): Boolean = true
    override fun role(): Role = Role.STUDENT

    fun isStudyingOrEnrolled(subject: Subject): Boolean =
        isEnrolled(subject) || degreeHistories.any { studiedDegree ->
            studiedDegree.studied_subjects.any { it.subject == subject && it.inProgress() }
        }

    fun isEnrolled(subject: Subject) = enrolledCourses.any { it.subject == subject }

    fun isStudyingAnyDegree(degrees: Collection<Degree>): Boolean =
        enrolledDegrees.any { enrolledDegree -> degrees.any { enrolledDegree.id!! == it.id!! } }

    fun addEnrolledCourse(course: Course) {
        if(enrolledCourses.none { it.id == course.id }){
            enrolledCourses.add(course)
        }
    }
    fun deleteEnrolledCourse(course: Course) = enrolledCourses.remove(course)

    fun anyCoefficientIsGreaterThan(number: Float) = degreeHistories.any {it.coefficient >= number}

    fun maxCoefficient(): Float {
        if (degreeHistories.isEmpty()) return 0f
        return degreeHistories.maxOf { it.coefficient }
    }

    fun getStudiedDegreeCoefficient(degree: Degree): Float = degreeHistories.find { it.degree == degree }?.coefficient ?: 0f

    fun getPassedSubjects(): List<Subject> {
        val result: MutableList<Subject> = mutableListOf()
        degreeHistories.forEach { studiedDegree ->
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

    fun addEnrolledDegree(degree: Degree) {
        if (enrolledDegrees.none { it.id == degree.id }){
            enrolledDegrees.add(degree)
        }
    }

    //addStudiedDegree: Return true if add element
    fun addStudiedDegree(studiedDegree: StudiedDegree): Boolean {
        if (degreeHistories.none { it.degree.id == studiedDegree.degree.id }){
            studiedDegree.student = this
            degreeHistories.add(studiedDegree)
            return true
        }
        return false
    }

    fun addOrUpdateStudiedDegree(studiedDegree: StudiedDegree) {
        if (addStudiedDegree(studiedDegree).not()){
            degreeHistories.forEach {
                if (it.degree.id == studiedDegree.degree.id ) {
                    it.isRegular = studiedDegree.isRegular
                    it.plan = studiedDegree.plan
                    it.quality = studiedDegree.quality
                    it.registryState = studiedDegree.registryState
                    it.location = studiedDegree.location
                }
            }
        }
    }

    fun getStudiedDegreeByDegree(degree: Degree): StudiedDegree =
        degreeHistories.find { it.degree.id == degree.id }!!

    fun existStudiedDegreeWithQuoteRequestCondition(degrees: MutableCollection<Degree>): Boolean =
        degreeHistories.any { studiedDegree ->
            degrees.any { studiedDegree.degree.id == it.id && studiedDegree.isQuoteRequestCondition() }
        }

    fun isNotStudyingInCourseLocation(course: Course) =
        degreeHistories.none { it.location == course.location }


    data class Builder(
        var id: Long? = null,
        var firstName: String = "",
        var lastName: String = "",
        var dni: String = "",
        var email: String = "",
        var password: String = "",
        var legajo: String = "",
        var enrolledDegrees: MutableCollection<Degree> = mutableListOf(),
        var degree_histories: MutableCollection<StudiedDegree> = mutableListOf(),
        var enrolledCourses: MutableCollection<Course> = mutableListOf(),
    ) {

        fun build(): Student = Student(id, firstName, lastName, dni, email, password, legajo, enrolledDegrees, degree_histories, enrolledCourses)
        fun withId(id: Long) = apply { this.id = id }
        fun withDni(dni: String) = apply {this.dni = dni}
        fun withLegajo(legajo: String) = apply {this.legajo = legajo}
    }
}