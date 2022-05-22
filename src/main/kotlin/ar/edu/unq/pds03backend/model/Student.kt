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
    val enrolledCourses: Collection<Course>
) : User(id, firstName, lastName, dni, email, username)
{
    override fun isStudent(): Boolean = true

    fun passed(subject: Subject): Boolean =
        degree_histories.any { studiedDegree -> studiedDegree.studied_subjects.any { it.subject == subject && it.passed() } }

    //TODO: Revisar si sigue aplicando utilizar "studiedSubject.inProgress()" si ahora existe la lista enrolledSubjects
    fun studiedOrEnrolled(subject: Subject): Boolean =
        degree_histories.any { studiedDegree ->
            studiedDegree.studied_subjects.any { it.subject == subject && it.inProgress() }
                    || enrolledCourses.any{ it.subject == subject }

        }
}