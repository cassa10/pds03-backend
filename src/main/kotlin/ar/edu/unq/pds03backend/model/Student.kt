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
    user: User,

    @Column(unique = true)
    val legajo: String,

    @OneToMany(mappedBy = "student")
    val degree_histories: Collection<StudiedDegree>
) : Person(id, firstName, lastName, dni, email, user)
{
    override fun isStudent(): Boolean = true

    fun passed(subject: Subject): Boolean =
        degree_histories.any { studiedDegree -> studiedDegree.studied_courses.any { studiedCourse -> studiedCourse.course.subject == subject } }
//    fun passed(subject: Subject): Boolean = subject.name == "Matemática"
}