package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.dto.csv.CsvSubjectWithPrerequisite
import ar.edu.unq.pds03backend.dto.subject.SubjectRequestDTO
import ar.edu.unq.pds03backend.exception.*
import ar.edu.unq.pds03backend.model.*
import ar.edu.unq.pds03backend.model.Subject
import ar.edu.unq.pds03backend.repository.*
import ar.edu.unq.pds03backend.service.impl.SubjectService
import ar.edu.unq.pds03backend.utils.QuoteStateHelper
import ar.edu.unq.pds03backend.utils.SemesterHelper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime
import java.util.Optional

class SubjectServiceTest {

    @RelaxedMockK
    private lateinit var subjectRepository: ISubjectRepository

    @RelaxedMockK
    private lateinit var degreeRepository: IDegreeRepository

    @RelaxedMockK
    private lateinit var courseRepository: ICourseRepository

    @RelaxedMockK
    private lateinit var userRepository: IUserRepository

    @RelaxedMockK
    private lateinit var quoteRequestRepository: IQuoteRequestRepository

    @RelaxedMockK
    private lateinit var semesterRepository: ISemesterRepository

    @RelaxedMockK
    private lateinit var configurableValidationRepository: IConfigurableValidationRepository

    @RelaxedMockK
    private lateinit var moduleRepository: IModuleRepository

    @RelaxedMockK
    private lateinit var externalSubjectRepository: IExternalSubjectRepository

    private lateinit var subjectService: SubjectService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        subjectService = SubjectService(
            subjectRepository,
            degreeRepository,
            courseRepository,
            userRepository,
            quoteRequestRepository,
            semesterRepository,
            configurableValidationRepository,
            moduleRepository,
            externalSubjectRepository
        )
    }

    @Test(expected = SubjectNotFoundException::class)
    fun `GIVEN no subject WHEN call getById with any id THEN return SubjectNotFoundException`() {
        val emptyOptional = Optional.empty<Subject>()
        every { subjectRepository.findById(any()) } returns emptyOptional
        subjectService.getById(10)
    }

    @Test
    fun `GIVEN a subject with id 1 WHEN call getById with id 1 THEN return that subject`() {
        val id: Long = 1
        val subject = Subject.Builder().withId(id).build()
        val maybeSubject = Optional.of(subject)
        every { subjectRepository.findById(id) } returns maybeSubject
        val returnedSubject = subjectService.getById(id)
        assertEquals(subject.id, returnedSubject.id)
    }

    @Test
    fun `GIVEN a list of subjects WHEN call getAll THEN return all subjects`() {
        val idSub1: Long = 1
        val idSub2: Long = 2
        val subject1 = Subject.Builder().withId(1).build()
        val subject2 = Subject.Builder().withId(2).build()
        every { subjectRepository.findAll() } returns listOf(subject1, subject2)
        val returnedSubjects = subjectService.getAll()
        assertEquals(2, returnedSubjects.size)
        assertTrue(returnedSubjects.any { it.id == idSub1 })
        assertTrue(returnedSubjects.any { it.id == idSub2 })
    }

    @Test
    fun `GIVEN a list of subjects WHEN call getAll with pageable THEN return all subjects as Page`() {
        val idSub1: Long = 1
        val idSub2: Long = 2
        val subject1 = Subject.Builder().withId(1).build()
        val subject2 = Subject.Builder().withId(2).build()
        val pageable: Pageable = Pageable.unpaged()
        val resultList = listOf(subject1, subject2)
        every { subjectRepository.findAll(pageable) } returns PageImpl(resultList, pageable, resultList.size.toLong())
        val returnedSubjects = subjectService.getAll(pageable)
        assertEquals(2, returnedSubjects.size)
        assertTrue(returnedSubjects.any { it.id == idSub1 })
        assertTrue(returnedSubjects.any { it.id == idSub2 })
    }

    @Test
    fun `GIVEN subject request dto WHEN create with it THEN Not Rise any Exception and subject is added in all degrees`() {
        val moduleId: Long = 1
        val subjectReqDTO = SubjectRequestDTO(listOf(1), "Subject 1", listOf(), moduleId)
        val degreeA = Degree(1, "A", "A", mutableListOf())
        val degreeB = Degree(2, "B", "B", mutableListOf())
        val maybeModule = Optional.of(Module(moduleId, "Module 1st", mutableListOf()))
        val degreesMustFound = listOf(degreeA, degreeB)
        val subjectSavedId: Long = 1
        every { degreeRepository.findAllById(any()) } returns degreesMustFound
        every { moduleRepository.findById(moduleId) } returns maybeModule
        every { subjectRepository.save(any()) } returns Subject.Builder().withId(subjectSavedId)
            .withPrerequisiteSubjects(mutableListOf()).withModule(maybeModule.get()).build()
        every { degreeRepository.saveAll(degreesMustFound) } returns degreesMustFound
        subjectService.create(subjectReqDTO)

        assertTrue(degreesMustFound.all { degreeFound -> degreeFound.subjects.any { it.id == subjectSavedId } })
        verify {
            subjectRepository.save(any())
            degreeRepository.saveAll(degreesMustFound)
        }
    }

    @Test(expected = ModuleNotFoundException::class)
    fun `GIVEN subject request dto WHEN create with it and module is not found THEN Rise ModuleNotFoundException`() {
        val subjectReqDTO = SubjectRequestDTO(listOf(1), "Subject 1", listOf(), 1)
        val degreeA = Degree(1, "A", "A", mutableListOf())
        val degreeB = Degree(2, "B", "B", mutableListOf())
        val moduleId: Long = 1
        val maybeModule = Optional.empty<Module>()
        val degreesMustFound = listOf(degreeA, degreeB)
        every { degreeRepository.findAllById(any()) } returns degreesMustFound
        every { moduleRepository.findById(moduleId) } returns maybeModule
        subjectService.create(subjectReqDTO)
    }

    @Test(expected = NotFoundAnyDegreeException::class)
    fun `GIVEN subject request dto WHEN create with it and any degree not found THEN Rise NotFoundAnyDegreeException`() {
        val subjectReqDTO = SubjectRequestDTO(listOf(1), "Subject 1", listOf(), 1)
        every { degreeRepository.findAllById(any()) } returns listOf()
        subjectService.create(subjectReqDTO)
    }


    @Test
    fun `GIVEN subject request dto and id WHEN update with both THEN Not Rise any Exception and subject update name, prerequiredSubjects and module and also degrees`() {
        val idSubject: Long = 1
        val moduleId: Long = 2
        val degreeAId: Long = 1
        val degreeCId: Long = 3
        val updateDegrees = listOf(degreeAId, degreeCId)
        val nameToUpdate = "Subject FA"
        val prerequisiteSubjectIdsToUpdate: List<Long> = listOf()
        val subjectReqDTO = SubjectRequestDTO(updateDegrees, nameToUpdate, prerequisiteSubjectIdsToUpdate, moduleId)
        val oldPrerequisiteSubjects = mutableListOf(Subject.Builder().withId(10).build())
        val oldSubject =
            Subject.Builder().withId(idSubject).withName("").withPrerequisiteSubjects(oldPrerequisiteSubjects)
                .withModule(Module(1, "1", mutableListOf())).build()

        val degreeA = Degree(degreeAId, "A", "A", mutableListOf(oldSubject))
        val degreeB = Degree(2, "B", "B", mutableListOf(oldSubject))
        val degreeC = Degree(degreeCId, "C", "C", mutableListOf())
        val oldDegrees = mutableListOf(degreeA, degreeB)
        oldSubject.degrees = oldDegrees
        val maybeModule = Optional.of(Module(moduleId, "$moduleId", mutableListOf()))
        val degreesMustFound = listOf(degreeA, degreeC)
        val subjectSavedId: Long = 1
        val prerequisiteSubjectToUpdate: List<Subject> = listOf()
        every { subjectRepository.findById(idSubject) } returns Optional.of(oldSubject)
        every { subjectRepository.findAllById(prerequisiteSubjectIdsToUpdate) } returns prerequisiteSubjectToUpdate
        every { degreeRepository.findAllById(updateDegrees) } returns degreesMustFound
        every { moduleRepository.findById(moduleId) } returns maybeModule
        every { subjectRepository.save(any()) } returns Subject.Builder().withId(subjectSavedId)
            .withPrerequisiteSubjects(mutableListOf()).withModule(maybeModule.get()).build()
        every { degreeRepository.saveAll(degreesMustFound) } returns degreesMustFound

        subjectService.update(idSubject, subjectReqDTO)

        assertEquals(oldSubject.module, maybeModule.get())
        assertEquals(oldSubject.name, nameToUpdate)
        assertEquals(oldSubject.prerequisiteSubjects, prerequisiteSubjectToUpdate)
        assertTrue(degreesMustFound.all { degreeFound -> degreeFound.subjects.any { it.id == subjectSavedId } })
        assertTrue(degreeB.subjects.none { it.id == subjectSavedId })

        verify {
            subjectRepository.save(any())
            degreeRepository.saveAll(degreesMustFound)
            degreeRepository.saveAll(listOf(degreeB))
        }
    }

    @Test
    fun `GIVEN an id of subject WHEN delete it THEN Not Rise any Exception and subject is deleted from their degrees`() {
        val idSubject: Long = 1
        val subject = Subject.Builder().withId(idSubject).build()
        val degreeA = Degree(1, "A", "A", mutableListOf(subject))
        val degreeB = Degree(2, "B", "B", mutableListOf(subject))
        val degrees = mutableListOf(degreeA, degreeB)
        subject.degrees = degrees

        every { subjectRepository.findById(idSubject) } returns Optional.of(subject)
        every { courseRepository.findBySubjectId(idSubject) } returns listOf()
        subjectService.delete(idSubject)


        verify {
            degreeRepository.saveAll(degrees)
            subjectRepository.delete(any())
        }
        assertTrue(degrees.none { degreeFound -> degreeFound.subjects.any { it.id == idSubject } })
    }

    @Test(expected = CannotDeleteSubjectWithCoursesException::class)
    fun `GIVEN an id of subject WHEN delete it and it has courses THEN Rise CannotDeleteSubjectWithCoursesException`() {
        val idSubject: Long = 1
        val subject = Subject.Builder().withId(idSubject).build()
        val degreeA = Degree(1, "A", "A", mutableListOf(subject))
        val degreeB = Degree(2, "B", "B", mutableListOf(subject))
        val degrees = mutableListOf(degreeA, degreeB)
        subject.degrees = degrees

        every { subjectRepository.findById(idSubject) } returns Optional.of(subject)
        every { courseRepository.findBySubjectId(idSubject) } returns listOf(Course.Builder().build())
        subjectService.delete(idSubject)
        verify { subjectRepository.delete(any()) }
    }

    @Test
    fun `GIVEN subjectsCsv WHEN createSubjects with them THEN Not Rise Any Exception and do nothing`() {
        val subjectsCsv: List<CsvSubjectWithPrerequisite> = listOf()
        val module = Module(1, "A", mutableListOf())
        every { moduleRepository.findByName(any()) } returns Optional.of(module)
        subjectService.createSubjects(subjectsCsv)
        verify(exactly = 0) { subjectRepository.findByGuaraniCode(any()) }
    }

    @Test(expected = ModuleNotFoundException::class)
    fun `GIVEN subjectsCsv WHEN createSubjects with them and module default not found THEN Raise ModuleNotFoundException`() {
        val subjectsCsv: List<CsvSubjectWithPrerequisite> = listOf()
        every { moduleRepository.findByName(any()) } returns Optional.empty()
        subjectService.createSubjects(subjectsCsv)
    }

    @Test
    fun `GIVEN external subject id that not exist and prerequisite subjects as external ids WHEN update prerequisite subjects with them and module default not found THEN do nothing`() {
        val externalSubjectId = "1010"
        val prerequisiteSubjectsExternalIds = listOf("1", "2")
        every { subjectRepository.findByGuaraniCode(externalSubjectId) } returns Optional.empty()
        subjectService.updatePrerequisiteSubjects(externalSubjectId, prerequisiteSubjectsExternalIds)
        verify(exactly = 0) { subjectRepository.save(any()) }
    }

    @Test
    fun `GIVEN external subject id and prerequisite subjects as external ids WHEN update prerequisite subjects with them and module default not found THEN subject update prerequisite subjects which are found`() {
        val externalSubjectId = "1010"
        val prerequisiteSubjectExtId1 = "1"
        val prerequisiteSubjectExtId2 = "2"
        val prerequisiteSubject2 = Subject.Builder().withId(2).withName("Subject $prerequisiteSubjectExtId2").build()
        val prerequisiteSubjectsExternalIds = listOf(prerequisiteSubjectExtId1, prerequisiteSubjectExtId2)
        val subject: Subject = Subject.Builder().withId(1010).withName("Subject $externalSubjectId")
            .withPrerequisiteSubjects(mutableListOf()).build()
        every { subjectRepository.findByGuaraniCode(externalSubjectId) } returns Optional.of(subject)
        every { subjectRepository.findByGuaraniCode(prerequisiteSubjectExtId1) } returns Optional.empty()
        every { subjectRepository.findByGuaraniCode(prerequisiteSubjectExtId2) } returns Optional.of(
            prerequisiteSubject2
        )
        every { subjectRepository.save(subject) } returns subject
        subjectService.updatePrerequisiteSubjects(externalSubjectId, prerequisiteSubjectsExternalIds)

        assertEquals(subject.prerequisiteSubjects.size, 1)
        assertTrue(subject.prerequisiteSubjects.contains(prerequisiteSubject2))
        verify { subjectRepository.save(subject) }
    }

    @Test(expected = SemesterNotFoundException::class)
    fun `GIVEN current semester not found WHEN get all current THEN Rise SemesterNotFoundException`() {
        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.empty()
        subjectService.getAllCurrent()
    }

    @Test
    fun `GIVEN current courses WHEN get all current THEN get all subjects with their current courses`() {
        val subject1Id: Long = 1
        val subject2Id: Long = 2
        val subject1 = Subject.Builder().withId(subject1Id).build()
        val subject2 = Subject.Builder().withId(subject2Id).build()
        val currentSemesterId: Long = 1
        val currentSemester = Semester(
            currentSemesterId,
            SemesterHelper.currentIsSecondSemester,
            SemesterHelper.currentYear,
            LocalDateTime.MIN,
            LocalDateTime.MAX
        )
        val courseId1: Long = 1
        val courseId2: Long = 2
        val courseId3: Long = 3
        val course1 = Course.Builder().withId(courseId1).withSubject(subject1).withSemester(currentSemester).build()
        val course2 = Course.Builder().withId(courseId2).withSubject(subject1).withSemester(currentSemester).build()
        val course3 = Course.Builder().withId(courseId3).withSubject(subject2).withSemester(currentSemester).build()
        val currentCourses = listOf(course1, course3, course2)
        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(currentSemester)
        every { courseRepository.findAllBySemesterId(currentSemesterId) } returns currentCourses

        val result = subjectService.getAllCurrent()
        assertEquals(result.size, 2)
        assertTrue(
            result.any {
                it.subject.id == subject1.id
                        && it.courses.size == 2
                        && it.courses.all { course -> course.id == courseId1 || course.id == courseId2 }
            }
        )
        assertTrue(
            result.any {
                it.subject.id == subject2.id
                        && it.courses.size == 1
                        && it.courses.any { course -> course.id == courseId3 }
            })
    }

    @Test
    fun `GIVEN current courses and id degree WHEN get all current by degree THEN get all subjects with their current courses filtered by that degree`() {
        val degreeIdToFilter: Long = 2
        val subject1Id: Long = 1
        val subject2Id: Long = 2
        val degreeA = Degree(1, "A", "A", mutableListOf())
        val degreeB = Degree(degreeIdToFilter, "B", "B", mutableListOf())
        val subject1 = Subject.Builder().withId(subject1Id).withDegrees(mutableListOf(degreeA)).build()
        val subject2 = Subject.Builder().withId(subject2Id).withDegrees(mutableListOf(degreeA, degreeB)).build()
        val currentSemesterId: Long = 1
        val currentSemester = Semester(
            currentSemesterId,
            SemesterHelper.currentIsSecondSemester,
            SemesterHelper.currentYear,
            LocalDateTime.MIN,
            LocalDateTime.MAX
        )
        val courseId1: Long = 1
        val courseId2: Long = 2
        val courseId3: Long = 3
        val course1 = Course.Builder().withId(courseId1).withSubject(subject1).withSemester(currentSemester).build()
        val course2 = Course.Builder().withId(courseId2).withSubject(subject1).withSemester(currentSemester).build()
        val course3 = Course.Builder().withId(courseId3).withSubject(subject2).withSemester(currentSemester).build()
        val currentCourses = listOf(course1, course3, course2)
        every { degreeRepository.findById(degreeIdToFilter) } returns Optional.of(degreeB)
        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(currentSemester)
        every { courseRepository.findAllBySemesterId(currentSemesterId) } returns currentCourses

        val result = subjectService.getAllCurrentByDegree(degreeIdToFilter)
        assertEquals(result.size, 1)
        assertTrue(
            result.any {
                it.subject.id == subject2.id
                        && it.courses.size == 1
                        && it.courses.all { course -> course.id == courseId3 }
            }
        )
    }

    @Test(expected = DegreeNotFoundException::class)
    fun `GIVEN current courses and id degree that not exist WHEN get all current by degree THEN Rise DegreeNotFoundException`() {
        val degreeIdToFilter: Long = 2
        every { degreeRepository.findById(degreeIdToFilter) } returns Optional.empty()
        subjectService.getAllCurrentByDegree(degreeIdToFilter)
    }

    @Test(expected = StudentNotFoundException::class)
    fun `GIVEN current courses and id student that not exist WHEN get all current by student THEN Rise StudentNotFoundException`() {
        val idStudentToFilter: Long = 2
        every { userRepository.findById(idStudentToFilter) } returns Optional.empty()
        subjectService.getAllCurrentByStudent(idStudentToFilter)
    }

    @Test(expected = PrerequisiteSubjectsValidationNotFoundException::class)
    fun `GIVEN current courses and student id WHEN get all current by student THEN Rise PrerequisiteSubjectsValidationNotFoundException`() {
        val idStudentToFilter: Long = 1
        val student = Student.Builder().withId(idStudentToFilter).build()
        val subject1Id: Long = 1
        val subject2Id: Long = 2
        val subject1 = Subject.Builder().withId(subject1Id).build()
        val subject2 = Subject.Builder().withId(subject2Id).build()
        val currentSemesterId: Long = 1
        val currentSemester = Semester(
            currentSemesterId,
            SemesterHelper.currentIsSecondSemester,
            SemesterHelper.currentYear,
            LocalDateTime.MIN,
            LocalDateTime.MAX
        )
        val courseId1: Long = 1
        val courseId2: Long = 2
        val courseId3: Long = 3
        val course1 = Course.Builder().withId(courseId1).withSubject(subject1).withSemester(currentSemester).build()
        val course2 = Course.Builder().withId(courseId2).withSubject(subject1).withSemester(currentSemester).build()
        val course3 = Course.Builder().withId(courseId3).withSubject(subject2).withSemester(currentSemester).build()
        val currentCourses = listOf(course1, course3, course2)
        val studentRequestedCourses = listOf(course3)
        every { userRepository.findById(idStudentToFilter) } returns Optional.of(student)
        every { quoteRequestRepository.findAllCoursesWithQuoteRequestInStatesAndStudentIdAndCourseSemesterId(QuoteStateHelper.getPendingStates(), idStudentToFilter, currentSemesterId) } returns studentRequestedCourses
        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(currentSemester)
        every { courseRepository.findAllBySemesterId(currentSemesterId) } returns currentCourses
        every { configurableValidationRepository.findByValidation(any()) } returns Optional.empty()
        subjectService.getAllCurrentByStudent(idStudentToFilter)
    }

    @Test
    fun `GIVEN current courses and student id and prerequisite subjects validation off WHEN get all current by student THEN get all subjects with their current courses filtered by student quote requested without prerequisite validation`() {
        val idStudentToFilter: Long = 1
        val student = Student.Builder().withId(idStudentToFilter).build()
        val subject1Id: Long = 1
        val subject2Id: Long = 2
        val subject1 = Subject.Builder().withId(subject1Id).build()
        val subject2 = Subject.Builder().withId(subject2Id).build()
        val currentSemesterId: Long = 1
        val currentSemester = Semester(
            currentSemesterId,
            SemesterHelper.currentIsSecondSemester,
            SemesterHelper.currentYear,
            LocalDateTime.MIN,
            LocalDateTime.MAX
        )
        val courseId1: Long = 1
        val courseId2: Long = 2
        val courseId3: Long = 3
        val course1 = Course.Builder().withId(courseId1).withSubject(subject1).withSemester(currentSemester).build()
        val course2 = Course.Builder().withId(courseId2).withSubject(subject1).withSemester(currentSemester).build()
        val course3 = Course.Builder().withId(courseId3).withSubject(subject2).withSemester(currentSemester).build()
        val currentCourses = listOf(course1, course3, course2)
        val studentRequestedCourses = listOf(course1)
        every { userRepository.findById(idStudentToFilter) } returns Optional.of(student)
        every { quoteRequestRepository.findAllCoursesWithQuoteRequestInStatesAndStudentIdAndCourseSemesterId(QuoteStateHelper.getPendingStates(), idStudentToFilter, currentSemesterId) } returns studentRequestedCourses
        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(currentSemester)
        every { courseRepository.findAllBySemesterId(currentSemesterId) } returns currentCourses
        every { configurableValidationRepository.findByValidation(any()) } returns Optional.of(ConfigurableValidation(1, Validation.PREREQUISITE_SUBJECTS, false))
        val result = subjectService.getAllCurrentByStudent(idStudentToFilter)
        assertEquals(result.size, 2)
        assertTrue(
            result.any {
                it.subject.id == subject1.id
                        && it.courses.size == 1
                        && it.courses.all { course -> course.id == courseId2 }
            }
        )
        assertTrue(
            result.any {
                it.subject.id == subject2.id
                        && it.courses.size == 1
                        && it.courses.all { course -> course.id == courseId3 }
            }
        )
    }

    @Test
    fun `GIVEN current courses and student id and prerequisite subjects validation on WHEN get all current by student THEN get all subjects with their current courses filtered by student quote requested with prerequisite validation`() {
        val idStudentToFilter: Long = 1
        val student = Student.Builder().withId(idStudentToFilter).build()
        val subject1Id: Long = 1
        val subject2Id: Long = 2
        val subject1 = Subject.Builder().withId(subject1Id).build()
        val subject2 = Subject.Builder().withId(subject2Id).build()
        val currentSemesterId: Long = 1
        val currentSemester = Semester(
            currentSemesterId,
            SemesterHelper.currentIsSecondSemester,
            SemesterHelper.currentYear,
            LocalDateTime.MIN,
            LocalDateTime.MAX
        )
        val courseId1: Long = 1
        val courseId2: Long = 2
        val courseId3: Long = 3
        val course1 = Course.Builder().withId(courseId1).withSubject(subject1).withSemester(currentSemester).build()
        val course2 = Course.Builder().withId(courseId2).withSubject(subject1).withSemester(currentSemester).build()
        val course3 = Course.Builder().withId(courseId3).withSubject(subject2).withSemester(currentSemester).build()
        val currentCourses = listOf(course1, course3, course2)
        val studentRequestedCourses = listOf(course1)
        every { userRepository.findById(idStudentToFilter) } returns Optional.of(student)
        every { quoteRequestRepository.findAllCoursesWithQuoteRequestInStatesAndStudentIdAndCourseSemesterId(QuoteStateHelper.getPendingStates(), idStudentToFilter, currentSemesterId) } returns studentRequestedCourses
        every { semesterRepository.findByYearAndIsSndSemester(any(), any()) } returns Optional.of(currentSemester)
        every { courseRepository.findAllBySemesterId(currentSemesterId) } returns currentCourses
        every { configurableValidationRepository.findByValidation(any()) } returns Optional.of(ConfigurableValidation(1, Validation.PREREQUISITE_SUBJECTS, true))
        val result = subjectService.getAllCurrentByStudent(idStudentToFilter)
        assertEquals(result.size, 2)
        assertTrue(
            result.any {
                it.subject.id == subject1.id
                        && it.courses.size == 1
                        && it.courses.all { course -> course.id == courseId2 }
            }
        )
        assertTrue(
            result.any {
                it.subject.id == subject2.id
                        && it.courses.size == 1
                        && it.courses.all { course -> course.id == courseId3 }
            }
        )
    }

}