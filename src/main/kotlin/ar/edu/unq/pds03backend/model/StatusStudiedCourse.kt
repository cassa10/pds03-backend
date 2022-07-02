package ar.edu.unq.pds03backend.model

enum class StatusStudiedCourse {
    IN_PROGRESS {
        override fun inProgress(): Boolean = true
        override fun toValueOfAcademyHistoriesFile(): String = "En Curso"
    },
    APPROVAL {
        override fun passed(): Boolean = true
        override fun toValueOfAcademyHistoriesFile(): String = "Aprobado"
    },
    PROMOTED {
        override fun passed(): Boolean = true
        override fun toValueOfAcademyHistoriesFile(): String = "Promocionado"
    },
    PENDING_APPROVAL {
        override fun inProgress(): Boolean = true
    };

    open fun passed(): Boolean = false
    open fun inProgress(): Boolean = false

    //TODO: Cambiar por los nuevos valores del csv
    open fun toValueOfAcademyHistoriesFile(): String = "Pendiente aprobación"
}