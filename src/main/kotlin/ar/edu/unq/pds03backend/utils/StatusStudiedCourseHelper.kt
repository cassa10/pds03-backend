package ar.edu.unq.pds03backend.utils

import ar.edu.unq.pds03backend.model.StatusStudiedCourse

object StatusStudiedCourseHelper {
    fun parseResultColumnAcademyHistoryFile(result: String): StatusStudiedCourse {
        return when (result) {
            "A" -> StatusStudiedCourse.APPROVED
            "E" -> StatusStudiedCourse.PENDING_APPROVAL
            "N" -> StatusStudiedCourse.DISAPPROVED
            "P" -> StatusStudiedCourse.PROMOTED
            "U" -> StatusStudiedCourse.ABSENT
            "R" -> StatusStudiedCourse.POSTPONED
            "V" -> StatusStudiedCourse.VIRTUAL_PENDING
            "" -> StatusStudiedCourse.ABSENT_EXAM
            else -> StatusStudiedCourse.IN_PROGRESS
        }
    }
}