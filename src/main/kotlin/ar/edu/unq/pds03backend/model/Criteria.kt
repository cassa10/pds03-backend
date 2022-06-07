package ar.edu.unq.pds03backend.model

interface Criteria {
    fun isEligibleStudent(student: Student): Boolean
}

class SimpleCriteria(private val predicate: (Student) -> Boolean) : Criteria {
    override fun isEligibleStudent(student: Student): Boolean = predicate(student)

    fun and(criteria: Criteria): Criteria = CompoundCriteria(CriteriaOperation.AND, this, criteria)
    fun or(criteria: Criteria): Criteria = CompoundCriteria(CriteriaOperation.OR, this, criteria)
}

class CompoundCriteria(
    private val operation: CriteriaOperation,
    private val criteriaLeft: Criteria,
    private val criteriaRight: Criteria,
) : Criteria {
    override fun isEligibleStudent(student: Student): Boolean = operation.apply(student, criteriaLeft, criteriaRight)
}

enum class CriteriaOperation {
    AND {
        override fun apply(student: Student, criteriaLeft: Criteria, criteriaRight: Criteria): Boolean =
            criteriaLeft.isEligibleStudent(student).and(criteriaRight.isEligibleStudent(student))

    },
    OR {
        override fun apply(student: Student, criteriaLeft: Criteria, criteriaRight: Criteria): Boolean =
            criteriaLeft.isEligibleStudent(student).or(criteriaRight.isEligibleStudent(student))
    };

    abstract fun apply(student: Student, criteriaLeft: Criteria, criteriaRight: Criteria): Boolean
}