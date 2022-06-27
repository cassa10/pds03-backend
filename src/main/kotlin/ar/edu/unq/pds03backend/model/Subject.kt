package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "subjects")
class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(unique = true, nullable = false)
    var name: String,

    @ManyToMany(
        mappedBy = "subjects",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH],
        fetch = FetchType.EAGER
    )
    val degrees: MutableCollection<Degree>,

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
    data class Builder(
        var id: Long? = null,
        var name: String = "",
        var degrees: MutableCollection<Degree> = mutableListOf(),
        var prerequisiteSubjects: MutableCollection<Subject> = mutableListOf(),
        var module: Module? = null
    ){
        fun build():Subject = Subject(id, name, degrees, prerequisiteSubjects, module!!)
        fun withName(name:String) = apply { this.name = name }
        fun withPrerequisiteSubjects(prerequisiteSubjects: MutableCollection<Subject>) = apply {this.prerequisiteSubjects = prerequisiteSubjects}
        fun withModule(module: Module) = apply { this.module = module }
    }
}
