package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

abstract class CannotProceedException(message: String) : RuntimeException(message)

private const val SUBJECT_CANNOT_DELETE = "subject cannot delete because has courses"
private const val COURSE_CANNOT_DELETE = "course cannot delete because has already quote request"
private const val STUDENT_HAS_ALREADY_ENROLLED_SUBJECT = "student has already enrolled with some subject"
private const val STUDENT_NOT_ENROLLED_IN_SOME_DEGREE = "student is not enrolled in some degree"
private const val QUOTE_REQUEST_CANNOT_DELETE = "quote request cannot be deleted because this state is approved or revoked"
private const val QUOTE_REQUEST_CANNOT_CREATE_EXPIRED = "quote request cannot be created because current semester limit dates have expired"
private const val STUDENT_NOT_APPLY_WITH_PREREQUISITE_SUBJECTS = "student is not passed all prerequisite subjects of some subject"

@ResponseStatus(code = HttpStatus.CONFLICT, reason = SUBJECT_CANNOT_DELETE)
class CannotDeleteSubjectWithCoursesException: CannotProceedException(SUBJECT_CANNOT_DELETE)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = COURSE_CANNOT_DELETE)
class CannotDeleteCourseException: CannotProceedException(COURSE_CANNOT_DELETE)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = STUDENT_HAS_ALREADY_ENROLLED_SUBJECT)
class StudentHasAlreadyEnrolledSubject: CannotProceedException(STUDENT_HAS_ALREADY_ENROLLED_SUBJECT)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = STUDENT_NOT_ENROLLED_IN_SOME_DEGREE)
class StudentNotEnrolledInSomeDegree: CannotProceedException(STUDENT_NOT_ENROLLED_IN_SOME_DEGREE)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = QUOTE_REQUEST_CANNOT_DELETE)
class CannotDeleteQuoteRequestException: CannotProceedException(QUOTE_REQUEST_CANNOT_DELETE)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = QUOTE_REQUEST_CANNOT_CREATE_EXPIRED)
class CannotCreateQuoteRequestException: CannotProceedException(QUOTE_REQUEST_CANNOT_CREATE_EXPIRED)

@ResponseStatus(code = HttpStatus.CONFLICT, reason = STUDENT_NOT_APPLY_WITH_PREREQUISITE_SUBJECTS)
class StudentNotApplyWithPrerequisiteSubjects: CannotProceedException(STUDENT_NOT_APPLY_WITH_PREREQUISITE_SUBJECTS)