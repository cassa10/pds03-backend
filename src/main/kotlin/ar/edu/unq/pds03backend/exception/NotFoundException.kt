package ar.edu.unq.pds03backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

private const val NEW_NOT_FOUND = "new not found"
private const val USER_NOT_FOUND = "User not found"
private const val STUDENT_NOT_FOUND = "Student not found"
private const val DEGREE_NOT_FOUND = "Degree not found"
private const val COURSE_NOT_FOUND = "Course not found"
private const val QUOTE_REQUEST_NOT_FOUND = "Quote request not found"
private const val SEMESTER_NOT_FOUND = "Semester not found"
private const val SUBJECT_NOT_FOUND = "Subject not found"
private const val ANY_DEGREE_NOT_FOUND = "any degree not found"
private const val CONFIGURABLE_VALIDATION_NOT_FOUND = "configurable validation not found"
private const val PREREQUISITE_SUBJECTS_VALIDATION_NOT_FOUND = "prerequisite subjects validation configuration not found"
private const val MODULE_NOT_FOUND = "Module not found"
private const val STUDIED_DEGREE_NOT_FOUND = "studied degree not found. Suggestion: import student in register students csv with present degree"

abstract class NotFoundException(message: String) : RuntimeException(message)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = USER_NOT_FOUND)
class UserNotFoundException : NotFoundException(USER_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = STUDENT_NOT_FOUND)
class StudentNotFoundException : NotFoundException(STUDENT_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = DEGREE_NOT_FOUND)
class DegreeNotFoundException : NotFoundException(DEGREE_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ANY_DEGREE_NOT_FOUND)
class NotFoundAnyDegreeException : NotFoundException(ANY_DEGREE_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = COURSE_NOT_FOUND)
class CourseNotFoundException : NotFoundException(COURSE_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = QUOTE_REQUEST_NOT_FOUND)
class QuoteRequestNotFoundException : NotFoundException(QUOTE_REQUEST_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = SEMESTER_NOT_FOUND)
class SemesterNotFoundException : NotFoundException(SEMESTER_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = SUBJECT_NOT_FOUND)
class SubjectNotFoundException : NotFoundException(SUBJECT_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = CONFIGURABLE_VALIDATION_NOT_FOUND)
class ConfigurableValidationNotFoundException: NotFoundException(CONFIGURABLE_VALIDATION_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = PREREQUISITE_SUBJECTS_VALIDATION_NOT_FOUND)
class PrerequisiteSubjectsValidationNotFoundException: NotFoundException(PREREQUISITE_SUBJECTS_VALIDATION_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = NEW_NOT_FOUND)
class NewNotFoundException: NotFoundException(NEW_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = MODULE_NOT_FOUND)
class ModuleNotFoundException: NotFoundException(MODULE_NOT_FOUND)

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = STUDIED_DEGREE_NOT_FOUND)
class StudiedDegreeNotFoundException: NotFoundException(STUDIED_DEGREE_NOT_FOUND)