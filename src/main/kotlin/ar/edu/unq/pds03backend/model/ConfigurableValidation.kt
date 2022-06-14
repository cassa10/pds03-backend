package ar.edu.unq.pds03backend.model

import javax.persistence.*

@Entity
@Table(name = "configurable_validation")
class ConfigurableValidation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(unique = true, nullable = false)
    val validation: Validation,
    @Column(nullable = false)
    var active: Boolean = false,
){
    fun validate(condition: Boolean): Boolean = active && condition
}

enum class Validation {
    PREREQUISITE_SUBJECTS
}