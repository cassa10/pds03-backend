package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.QuoteRequestSubjectPendingResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.AdminCommentRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestRequestDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestResponseDTO
import ar.edu.unq.pds03backend.dto.quoteRequest.QuoteRequestWithWarningsResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesAndSubjectsResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithQuotesInfoResponseDTO
import ar.edu.unq.pds03backend.dto.student.StudentWithRequestedQuotesResponseDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.mapper.QuoteRequestMapper
import ar.edu.unq.pds03backend.mapper.QuoteRequestSubjectMapper
import ar.edu.unq.pds03backend.mapper.StudentMapper
import ar.edu.unq.pds03backend.model.*
import ar.edu.unq.pds03backend.repository.*
import ar.edu.unq.pds03backend.service.IQuoteRequestService
import ar.edu.unq.pds03backend.utils.QuoteStateHelper
import ar.edu.unq.pds03backend.utils.SemesterHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class QuoteRequestService(
    @Autowired private val quoteRequestRepository: IQuoteRequestRepository,
    @Autowired private val courseRepository: ICourseRepository,
    @Autowired private val studentRepository: IStudentRepository,
    @Autowired private val semesterRepository: ISemesterRepository,
    @Autowired private val configurableValidationRepository: IConfigurableValidationRepository,
) : IQuoteRequestService {

    companion object {
        //Criteria
        const val minCoefficientCriteria = 6f
        //Warnings
        const val prerequisiteWarningMessage = "student not apply with all prerequisite subjects"
        const val minCoefficientWarning = 3f
        const val minCoefficientWarningMessage = "student max coefficient is lesser than $minCoefficientWarning"
    }

    @Transactional
    override fun create(quoteRequestRequestDTO: QuoteRequestRequestDTO) {
        val currentSemester = getCurrentSemester()
        if (!currentSemester.isAcceptQuoteRequestsAvailable()) throw CannotCreateQuoteRequestException()

        val courses = courseRepository.findAllById(quoteRequestRequestDTO.idCourses)
        if (courses.isEmpty()) throw CourseNotFoundException()

        val student = getStudent(quoteRequestRequestDTO.idStudent)

        if (courses.any { !student.isStudyingAnyDegree(it.subject.degrees) }) throw StudentNotEnrolledInSomeDegree()
        if (courses.any { student.isStudyingOrEnrolled(it.subject) }) throw StudentHasAlreadyEnrolledSubject()

        val prerequisiteSubjectsValidation = getPrerequisiteSubjectsValidation()
        if (prerequisiteSubjectsValidation.validate(courses.any { !student.passedAllPrerequisiteSubjects(it.subject) })) throw StudentNotApplyWithPrerequisiteSubjects()

        //Verify criteria of student if to set auto-approved or pending state
        val crt = SimpleCriteria { it.anyCoefficientIsGreaterThan(minCoefficientCriteria) }
        val initialState = getInitialState(student, crt)

        //If quoteRequest was already created by that student, there are skipped
        courses.forEach {
            val quoteRequest = quoteRequestRepository.findByCourseIdAndStudentId(it.id!!, student.id!!)
            if (!quoteRequest.isPresent) {
                quoteRequestRepository.save(
                    QuoteRequest(
                        course = it,
                        student = student,
                        state = initialState,
                        comment = quoteRequestRequestDTO.comment,
                        createdOn = LocalDateTime.now(),
                    )
                )
            }
        }

    }

    override fun getById(id: Long): QuoteRequestWithWarningsResponseDTO {
        val warningSeekers: List<QuoteRequestWarningSeeker> = listOf(
            getHoursWarningSeeker(),
            getPrerequisiteWarningSeeker(),
            getMinCoefficientWarningSeeker()
        )
        val quoteRequest = getQuoteRequest(id)
        return QuoteRequestWithWarningsResponseDTO.Mapper(
            quoteRequest,
            warningSeekers.mapNotNull { it.apply(quoteRequest) }
        ).map()
    }

    override fun getAll(quoteStates: Set<QuoteState>): List<QuoteRequestResponseDTO> {
        val quoteRequests = quoteRequestRepository.findAllByInStates(quoteStates, getSortByCreatedOnAsc())
        return quoteRequests.map { QuoteRequestMapper.toDTO(it) }
    }

    override fun getAllByCourseAndStudent(
        idCourse: Long,
        idStudent: Long,
        quoteStates: Set<QuoteState>
    ): List<QuoteRequestResponseDTO> {
        val course = courseRepository.findById(idCourse)
        if (!course.isPresent) throw CourseNotFoundException()

        val student = getStudent(idStudent)

        val quoteRequests = quoteRequestRepository.findAllByCourseIdAndStudentIdAndInStates(
            course.get().id!!,
            student.id!!,
            quoteStates,
            getSortByCreatedOnAsc()
        )
        return quoteRequests.map { QuoteRequestMapper.toDTO(it) }
    }

    override fun getAllByCourse(idCourse: Long, quoteStates: Set<QuoteState>): List<QuoteRequestResponseDTO> {
        val course = courseRepository.findById(idCourse)
        if (!course.isPresent) throw CourseNotFoundException()

        val quoteRequests =
            quoteRequestRepository.findAllByCourseIdAndInStates(course.get().id!!, quoteStates, getSortByCreatedOnAsc())
        return quoteRequests.map { QuoteRequestMapper.toDTO(it) }
    }

    override fun getAllCurrentSemesterByStudent(
        idStudent: Long,
        quoteStates: Set<QuoteState>
    ): List<QuoteRequestResponseDTO> {
        val student = getStudent(idStudent)
        val currentSemester = getCurrentSemester()
        val quoteRequests = quoteRequestRepository.findAllByStudentIdAndCourseSemesterIdAndInStates(
            student.id!!,
            currentSemester.id!!,
            quoteStates,
            getSortByCreatedOnAsc()
        )
        return quoteRequests.map { QuoteRequestMapper.toDTO(it) }
    }

    override fun getQuoteRequestSubjects(states: Set<QuoteState>): List<QuoteRequestSubjectPendingResponseDTO> {
        val semester = getCurrentSemester()
        val quoteRequestSubjectsPending =
            quoteRequestRepository.findAllByInStatesAndCourseSemesterId(states, semester.id!!, getSortByCreatedOnAsc())

        // TODO (REFACTOR): refactor in query
        val list = mutableListOf<Long>()
        return quoteRequestSubjectsPending.filter {
            !list.contains(it.course.id).apply {
                list.add(it.course.id!!)
            }
        }.map {
            QuoteRequestSubjectMapper(
                quoteRequestRepository.countByInStatesAndCourseId(setOf(QuoteState.APPROVED), it.course.id!!),
                quoteRequestRepository.countByInStatesAndCourseId(QuoteStateHelper.getPendingStates(), it.course.id!!)
            ).map(it)
        }
    }

    @Transactional
    override fun addAdminComment(idQuoteRequest: Long, adminCommentRequestDTO: AdminCommentRequestDTO) {
        //TODO (JWT): Add JWT and validate if it belongs to DIRECTOR rol type.
        val quoteRequest = getQuoteRequest(idQuoteRequest)
        quoteRequest.adminComment = adminCommentRequestDTO.comment
        quoteRequestRepository.save(quoteRequest)
    }

    override fun findAllStudentsWithQuoteRequestCurrentSemester(states: Set<QuoteState>): List<StudentWithQuotesInfoResponseDTO> {
        val semester = getCurrentSemester()
        val studentsWithQuoteRequests =
            quoteRequestRepository.findAllStudentsWithQuoteRequestInStatesAndCourseSemesterId(states, semester.id!!)
        return studentsWithQuoteRequests.map {
            val numberOfPendingQuoteRequest = quoteRequestRepository.countByInStatesAndStudentIdAndCourseSemesterId(
                QuoteStateHelper.getPendingStates(),
                it.id!!,
                semester.id!!
            )
            StudentWithQuotesInfoResponseDTO.Mapper(it, numberOfPendingQuoteRequest).map()
        }
    }

    override fun findAllStudentsWithQuoteRequestInSubjectCurrentSemester(
        idSubject: Long,
        states: Set<QuoteState>
    ): List<StudentWithQuotesAndSubjectsResponseDTO> {
        val currentSemester = getCurrentSemester()
        val studentsWithQuoteRequestsToSubject =
            quoteRequestRepository.findAllStudentsWithQuoteRequestInStatesAndSubjectIdAndCourseSemesterId(
                states, idSubject, currentSemester.id!!
            )
        return studentsWithQuoteRequestsToSubject.map {
            val quoteRequests = quoteRequestRepository.findAllByStudentIdAndCourseSemesterIdAndInStates(
                it.id!!,
                currentSemester.id!!,
                QuoteStateHelper.getAllStates(),
                getSortByCreatedOnAsc()
            )
            StudentMapper.toStudentWithQuotesAndSubjectsResponseDTO(it, quoteRequests)
        }
    }

    @Transactional
    override fun delete(id: Long) {
        val quoteRequest = getQuoteRequest(id)
        if (quoteRequest.state != QuoteState.PENDING) throw CannotDeleteQuoteRequestException()
        quoteRequestRepository.deleteById(quoteRequest.id!!)
    }

    override fun findStudentWithQuoteRequests(
        idStudent: Long,
        states: Set<QuoteState>
    ): StudentWithRequestedQuotesResponseDTO {
        val student = getStudent(idStudent)
        val currentSemester = getCurrentSemester()
        val quoteRequests = quoteRequestRepository.findAllByInStatesAndStudentIdAndCourseSemesterId(
            states,
            student.id!!,
            currentSemester.id!!,
            getSortByCreatedOnAsc()
        )
        return StudentWithRequestedQuotesResponseDTO.Mapper(student, quoteRequests).map()
    }

    @Transactional
    override fun acceptQuoteRequest(id: Long) {
        //TODO (JWT):  Add JWT and validate if it belongs to DIRECTOR rol type.
        val quoteRequest = getQuoteRequest(id)
        //Validate if student was already accepted in other quote request on same subject
        if (quoteRequest.student.isEnrolled(quoteRequest.course.subject)) throw StudentHasAlreadyEnrolledSubject()
        quoteRequest.accept()
        quoteRequestRepository.save(quoteRequest)
    }

    @Transactional
    override fun revokeQuoteRequest(id: Long) {
        //TODO (JWT):  Add JWT and validate if it belongs to DIRECTOR rol type.
        val quoteRequest = getQuoteRequest(id)
        quoteRequest.revoke()
        quoteRequestRepository.save(quoteRequest)
    }

    @Transactional
    override fun rollbackToPendingRequest(id: Long) {
        //TODO (JWT):  Add JWT and validate if it belongs to DIRECTOR rol type.
        val quoteRequest = getQuoteRequest(id)
        quoteRequest.rollbackToPending()
        quoteRequestRepository.save(quoteRequest)
    }

    private fun getQuoteRequest(id: Long): QuoteRequest {
        val quoteRequest = quoteRequestRepository.findById(id)
        if (!quoteRequest.isPresent) throw QuoteRequestNotFoundException()
        return quoteRequest.get()
    }

    private fun getStudent(idStudent: Long): Student {
        val student = studentRepository.findById(idStudent)
        if (!student.isPresent) throw StudentNotFoundException()
        return student.get()
    }

    private fun getCurrentSemester(): Semester {
        return getSemester(SemesterHelper.currentYear, SemesterHelper.currentIsSecondSemester)
    }

    private fun getSemester(year: Int, isSndSemester: Boolean): Semester {
        val maybeSemester = semesterRepository.findByYearAndIsSndSemester(year, isSndSemester)
        if (!maybeSemester.isPresent) throw SemesterNotFoundException()
        return maybeSemester.get()
    }

    private fun getSortByCreatedOnAsc(): Sort = Sort.by(Sort.Direction.ASC, QuoteRequest.createdOnFieldName)

    private fun getInitialState(student: Student, criteria: Criteria): QuoteState {
        if (criteria.isEligibleStudent(student)) {
            return QuoteState.AUTOAPPROVED
        }
        return QuoteState.PENDING
    }

    private fun getPrerequisiteSubjectsValidation(): ConfigurableValidation {
        val maybeConfigValidation = configurableValidationRepository.findByValidation(Validation.PREREQUISITE_SUBJECTS)
        if (maybeConfigValidation.isEmpty) throw PrerequisiteSubjectsValidationNotFoundException()
        return maybeConfigValidation.get()
    }

    //TODO (REFACTOR): Move to another class this methods
    private fun getAllHoursOfStudent(student: Student): List<Hour> {
        val allPendingQuoteRequest = quoteRequestRepository.findAllByInStatesAndStudentIdAndCourseSemesterId(
            QuoteStateHelper.getPendingStates(),
            student.id!!,
            getCurrentSemester().id!!,
            getSortByCreatedOnAsc()
        )
        return allPendingQuoteRequest.flatMap { it.course.hours }.plus(student.enrolledCourses.flatMap { it.hours })
    }

    //TODO (REFACTOR): Move to another class this methods
    private fun getHoursWarningSeeker(): QuoteRequestWarningSeeker =
        QuoteRequestWarningSeeker { quoteRequest ->
            val allHours: List<Hour> = getAllHoursOfStudent(quoteRequest.student)
            when (val maybeHour: Hour? = quoteRequest.course.hours.find { it.anyIntercept(allHours) }) {
                null -> null
                else -> Warning(WarningType.CRITICAL, "student has schedules in conflict with hour: ${maybeHour.String()}")
            }
        }

    //TODO (REFACTOR): Move to another class this methods
    private fun getPrerequisiteWarningSeeker(): QuoteRequestWarningSeeker =
        QuoteRequestWarningSeeker {
            when (!it.student.passedAllPrerequisiteSubjects(it.course.subject)) {
                true -> Warning(WarningType.MEDIUM, prerequisiteWarningMessage)
                else -> null
            }
        }

    //TODO (REFACTOR): Move to another class this methods
    private fun getMinCoefficientWarningSeeker(): QuoteRequestWarningSeeker =
        QuoteRequestWarningSeeker {
            when (!it.student.anyCoefficientIsGreaterThan(minCoefficientWarning)) {
                true -> Warning(WarningType.LOW, minCoefficientWarningMessage)
                else -> null
            }
        }
}
