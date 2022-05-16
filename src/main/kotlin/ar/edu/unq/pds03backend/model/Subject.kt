package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "subjects")
class Subject(
    @Id
    @GeneratedValue
    val id: Long?,

    @Column(unique = true, nullable = false)
    var name: String,

    @ManyToMany(
        mappedBy = "subjects",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH],
        fetch = FetchType.EAGER
    )
    val degrees: MutableCollection<Degree>,
){
    data class Builder(
        var id: Long? = null,
        var name: String = "",
        var degrees: Collection<Degree> = mutableListOf(),
    ){
        fun build():Subject = Subject(id, name, mutableListOf())
        fun withName(name:String) = apply { this.name = name }
    }
}
