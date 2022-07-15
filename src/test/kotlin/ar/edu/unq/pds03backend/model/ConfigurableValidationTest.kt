package ar.edu.unq.pds03backend.model

import junit.framework.TestCase.*
import org.junit.Test

class ConfigurableValidationTest {

    @Test
    fun `test configurable validation validate with condition`(){
        val condition1 = true
        val condition2 = false

        val active1 = true
        val active2 = false
        val id1: Long = 1
        val id2: Long = 2
        val configVal1 = ConfigurableValidation(id1, Validation.PREREQUISITE_SUBJECTS, active1)
        val configVal2 = ConfigurableValidation(id2, Validation.PREREQUISITE_SUBJECTS, active2)

        assertEquals(configVal1.id, id1)
        assertEquals(configVal2.id, id2)
        assertEquals(configVal2.validation, Validation.PREREQUISITE_SUBJECTS)
        assertEquals(configVal2.validation, Validation.PREREQUISITE_SUBJECTS)
        assertTrue(configVal1.validate(condition1))
        assertFalse(configVal1.validate(condition2))
        assertFalse(configVal2.validate(condition1))
        assertFalse(configVal2.validate(condition2))
    }
}