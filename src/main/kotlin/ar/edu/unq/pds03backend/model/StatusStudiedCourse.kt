package ar.edu.unq.pds03backend.model

enum class StatusStudiedCourse {
    //TODO: Es necesario IN_PROGRESS ya que existe en student "enrolledCourses"??
    IN_PROGRESS {
        override fun inProgress(): Boolean = true
    },
    APPROVAL {
        override fun passed(): Boolean = true
    },
    PROMOTED {
        override fun passed(): Boolean = true
    },
    PENDING_APPROVAL {
        override fun inProgress(): Boolean = true
    };

    open fun passed(): Boolean = false
    open fun inProgress(): Boolean = false
}