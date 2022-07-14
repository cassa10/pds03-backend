package ar.edu.unq.pds03backend.model

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.Test

class DirectorTest {

    @Test
    fun `test director init with role DIRECTOR and is not student`() {
        val idParam: Long = 1
        val firstNameParam = "f"
        val lastNameParam = "l"
        val dniParam = "123"
        val emailParam = "asd"
        val passwordParam = "p"
        val director = Director(idParam, firstNameParam, lastNameParam, dniParam, emailParam, passwordParam)
        with(director) {
            assertEquals(id, idParam)
            assertEquals(firstName, firstNameParam)
            assertEquals(lastName, lastNameParam)
            assertEquals(dni, dniParam)
            assertEquals(email, emailParam)
            assertEquals(password, passwordParam)
            assertEquals(role(),Role.DIRECTOR)
            assertFalse(isStudent())
        }
    }
}