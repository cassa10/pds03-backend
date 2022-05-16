package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "degrees")
class Degree(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var name: String,

    @Column(unique = true, nullable = false)
    var acronym: String,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinTable(
        name = "degree_subject",
        joinColumns = [JoinColumn(name = "degree_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "subject_id", referencedColumnName = "id")]
    )
    val subjects: MutableCollection<Subject>
) {
    fun addSubject(subject: Subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject)
        }
    }

    fun deleteSubject(subject: Subject) {
        subjects.remove(subject)
    }

    data class Builder(
        var id: Long? = null,
        var name: String = "",
        var acronym: String = "",
        var subjects: MutableCollection<Subject> = mutableListOf()
    ){
        fun build():Degree = Degree(id, name, acronym, subjects)
        fun withId(id:Long) = apply { this.id = id }
        fun withName(name:String) = apply { this.name = name }
        fun withAcronym(acronym: String) = apply { this.acronym = acronym }
        fun withSubjects(subjects: MutableCollection<Subject>) = apply { this.subjects = subjects }
    }
}