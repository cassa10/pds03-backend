package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "subjects")
class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false)
    var name: String,

    @ManyToMany(
        mappedBy = "subjects",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH],
        fetch = FetchType.EAGER
    )
    var degrees: MutableCollection<Degree>,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinTable(
        name = "prerequisite_subjects",
        joinColumns = [JoinColumn(name = "subject_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "prerequisite_subject_id", referencedColumnName = "id")]
    )
    var prerequisiteSubjects: MutableCollection<Subject>,

    @ManyToOne
    @JoinColumn(name="module_id")
    var module: Module
){
    fun addPrerequisiteSubject(prerequisiteSubject: Subject) {
        if(id != prerequisiteSubject.id && prerequisiteSubjects.none { it.id == prerequisiteSubject.id }){
            prerequisiteSubjects.add(prerequisiteSubject)
        }
    }

    data class Builder(
        private var id: Long? = null,
        private var name: String = "",
        private var degrees: MutableCollection<Degree> = mutableListOf(),
        private var prerequisiteSubjects: MutableCollection<Subject> = mutableListOf(),
        private var module: Module = Module()
    ){
        fun build():Subject = Subject(id, name, degrees, prerequisiteSubjects, module)
        fun withId(id: Long) = apply {this.id = id}
        fun withName(name:String) = apply { this.name = name }
        fun withPrerequisiteSubjects(prerequisiteSubjects: MutableCollection<Subject>) = apply {this.prerequisiteSubjects = prerequisiteSubjects}
        fun withModule(module: Module) = apply { this.module = module }
        fun withDegrees(degrees: MutableCollection<Degree>) = apply { this.degrees = degrees }
    }
}
