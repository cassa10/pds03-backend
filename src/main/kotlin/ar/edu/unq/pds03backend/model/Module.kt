package ar.edu.unq.pds03backend.model

import javax.persistence.*

@Entity
@Table(name = "modules")
class Module(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var name: String = "",

    @OneToMany(cascade =  [CascadeType.ALL])
    var subjects: MutableCollection<Subject> = mutableListOf()
) {

}
