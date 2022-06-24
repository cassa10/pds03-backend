package ar.edu.unq.pds03backend.model


import ar.edu.unq.pds03backend.model.SimpleCriteria
import ar.edu.unq.pds03backend.model.Student
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CriteriaTest {

    @Test
    fun testGivenSimpleCriteriaWithConstPredicateWhenApplyIsEligibleStudentWithAnyStudentThenReturnEvaluationOfThatPredicateConst() {
        val studentA = Student.Builder().build()
        val studentB = Student.Builder().build()
        val criteriaConstTrue = SimpleCriteria { true }
        val criteriaConstFalse = SimpleCriteria { false }
        Assertions.assertTrue(criteriaConstTrue.isEligibleStudent(studentA))
        Assertions.assertTrue(criteriaConstTrue.isEligibleStudent(studentB))
        Assertions.assertFalse(criteriaConstFalse.isEligibleStudent(studentB))
        Assertions.assertFalse(criteriaConstFalse.isEligibleStudent(studentB))
    }

    @Test
    fun testGivenSimpleCriteriaWithPredicateWhenApplyIsEligibleStudentThenReturnApplicationOfThatPredicateWithStudent() {
        val studentA = Student.Builder().withDni("1").build()
        val studentB = Student.Builder().withDni("2").build()
        val criteria = SimpleCriteria { it.dni == "1" }
        Assertions.assertTrue(criteria.isEligibleStudent(studentA))
        Assertions.assertFalse(criteria.isEligibleStudent(studentB))
    }

    @Test
    fun testGivenTwoSimpleCriteriaWithTheirPredicateWhenAppendOrWithAnyCriteriaAndApplyIsEligibleStudentThenReturnApplicationOfBothPredicatesOperatedWithLogicalOrWithSameStudent() {
        val studentA = Student.Builder().withDni("1").build()
        val studentB = Student.Builder().withDni("2").build()
        val studentC = Student.Builder().withDni("3").build()
        val criteriaA = SimpleCriteria { it.dni == "1" }
        val criteriaB = SimpleCriteria { it.dni == "2" }
        val criteriaCompA = criteriaA.or(criteriaB)
        val criteriaCompB = criteriaB.or(criteriaA)
        Assertions.assertTrue(criteriaCompA.isEligibleStudent(studentA))
        Assertions.assertTrue(criteriaCompA.isEligibleStudent(studentB))
        Assertions.assertFalse(criteriaCompA.isEligibleStudent(studentC))
        Assertions.assertTrue(criteriaCompB.isEligibleStudent(studentA))
        Assertions.assertTrue(criteriaCompB.isEligibleStudent(studentB))
        Assertions.assertFalse(criteriaCompB.isEligibleStudent(studentC))
    }

    @Test
    fun testGivenTwoSimpleCriteriaWithTheirPredicateWhenAppendAndWithAnyCriteriaAndApplyIsEligibleStudentThenReturnApplicationOfBothPredicatesOperatedWithLogicalAndWithSameStudent() {
        val studentA = Student.Builder().withDni("1").withLegajo("A").build()
        val studentB = Student.Builder().withDni("2").withLegajo("A").build()
        val criteriaA = SimpleCriteria { it.dni == "1" }
        val criteriaB = SimpleCriteria { it.legajo == "A" }
        val criteriaCompA = criteriaA.and(criteriaB)
        val criteriaCompB = criteriaB.and(criteriaA)
        Assertions.assertTrue(criteriaCompA.isEligibleStudent(studentA))
        Assertions.assertFalse(criteriaCompA.isEligibleStudent(studentB))
        Assertions.assertTrue(criteriaCompB.isEligibleStudent(studentA))
        Assertions.assertFalse(criteriaCompB.isEligibleStudent(studentB))
    }
}