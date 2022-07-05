package ar.edu.unq.pds03backend.model

enum class StatusStudiedCourse {
    APPROVED {
        override fun passed(): Boolean = true
        override fun translate(): String = "Aprobado"
    },
    PENDING_APPROVAL {
        override fun inProgress(): Boolean = true
        override fun translate(): String = "Pendiente Aprobación"
    },
    DISAPPROVED {
        override fun translate(): String = "Desaprobado"
                },
    PROMOTED {
        override fun passed(): Boolean = true
        override fun translate(): String = "Promocionado"
    },
    ABSENT {
        override fun translate(): String = "Ausente"
           },
    POSTPONED {
        override fun translate(): String = "Aplazo"
              },
    VIRTUAL_PENDING {
        override fun translate(): String = "Pendiente Virtual"
                    },
    ABSENT_EXAM {
        override fun translate(): String = "Ausente de Examen"
                },
    IN_PROGRESS {
        override fun inProgress(): Boolean = true
        override fun translate(): String = "En curso"
    };

    open fun passed(): Boolean = false
    open fun inProgress(): Boolean = false
    open fun translate(): String = ""
}