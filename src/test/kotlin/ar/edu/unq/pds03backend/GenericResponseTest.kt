package ar.edu.unq.pds03backend

import ar.edu.unq.pds03backend.dto.GenericResponse
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.springframework.http.HttpStatus

class GenericResponseTest {

    @Test
    fun `test generic response init`(){
        val httpStatus1 = HttpStatus.OK
        val message1 = "msg1"
        val httpStatus2 = HttpStatus.NOT_FOUND
        val message2 = "msg error"
        val res1 = GenericResponse(httpStatus1, message1)
        val res2 = GenericResponse(httpStatus2, message2)

        with(res1) {
            assertEquals(httpStatus, httpStatus1)
            assertEquals(message, message1)
            assertEquals(status, httpStatus1.name)
            assertEquals(statusCode, httpStatus1.value())
        }
        with(res2) {
            assertEquals(httpStatus, httpStatus2)
            assertEquals(message, message2)
            assertEquals(status, httpStatus2.name)
            assertEquals(statusCode, httpStatus2.value())
        }
    }
}