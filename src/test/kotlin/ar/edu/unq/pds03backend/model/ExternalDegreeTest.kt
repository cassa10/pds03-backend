package ar.edu.unq.pds03backend.model

import junit.framework.TestCase
import org.junit.Test

class ExternalDegreeTest {

    @Test
    fun `test init external degree`(){
        val id: Long = 1
        val degree = Degree.Builder().build()
        val guaraniCode = "D1"
        val externalDegree = ExternalDegree(id, degree, guaraniCode)

        TestCase.assertEquals(externalDegree.id, id)
        TestCase.assertEquals(externalDegree.degree, degree)
        TestCase.assertEquals(externalDegree.guarani_code, guaraniCode)
    }
}