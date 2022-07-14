package ar.edu.unq.pds03backend.model

import javax.persistence.*;

@Entity
@Table(name = "studied_degrees")
class StudiedDegree(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,

    @ManyToOne @JoinColumn(name = "degree_id") val degree: Degree,

    @ManyToOne @JoinColumn(name = "student_id") var student: Student,

    @Column(nullable = false) var coefficient: Float,

    @Column(nullable = false, name = "registry_state") var registryState: RegistryState,

    @Column(nullable = false) var quality: QualityState,

    @Column(nullable = false) var plan: String,

    @Column(nullable = false, name = "is_regular") var isRegular: Boolean,

    @Column(nullable = false) var location: String,

    @OneToMany(mappedBy = "studiedDegree") val studied_subjects: Collection<StudiedSubject>,
) {
    fun isQuoteRequestCondition(): Boolean =
        registryState == RegistryState.ACCEPTED && quality == QualityState.ACTIVE && isRegular

    data class Builder(
        private var id: Long? = null,
        private var degree: Degree = Degree.Builder().build(),
        private var student: Student = Student.Builder().build(),
        private var coefficient: Float = 0f,
        private var registryState: RegistryState = RegistryState.PENDING,
        private var plan: String = "",
        private var quality: QualityState = QualityState.PASSIVE,
        private var location: String = "",
        private var isRegular: Boolean = false,
        private var studied_subjects: Collection<StudiedSubject> = listOf(),
    ) {
        fun build(): StudiedDegree = StudiedDegree(
            id = id,
            degree = degree,
            student = student,
            coefficient = coefficient,
            registryState = registryState,
            plan = plan,
            quality = quality,
            isRegular = isRegular,
            location = location,
            studied_subjects = studied_subjects,
        )

        fun withId(id: Long) = apply { this.id = id }
        fun withCoefficient(coefficient: Float) = apply { this.coefficient = coefficient }
        fun withStudent(student: Student) = apply { this.student = student }
        fun withDegree(degree: Degree) = apply { this.degree = degree }
        fun withRegistryState(registryState: RegistryState) = apply { this.registryState = registryState }
        fun withPlan(plan: String) = apply { this.plan = plan }
        fun withQuality(quality: QualityState) = apply { this.quality = quality }
        fun withIsRegular(isRegular: Boolean) = apply { this.isRegular = isRegular }
        fun withLocation(location: String) = apply { this.location = location }
        fun withStudiedSubjects(studiedSubjects: Collection<StudiedSubject>) = apply { this.studied_subjects = studiedSubjects }
    }
}
