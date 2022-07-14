package ar.edu.unq.pds03backend.model

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class NewTest {

    @Test
    fun `test new init`() {

        val id1: Long = 1
        val title1: String = "t1"
        val message1: String = "m1"
        val imageSource1: String = "img1"
        val imageAlt1: String = "alt1"
        val createdOn1: LocalDateTime = LocalDateTime.MIN
        val writer1: String = "w1"
        val email1: String = "e1"
        val new1 = New(id1, title1, message1, imageSource1, imageAlt1, createdOn1, writer1, email1)

        val id2: Long = 2
        val title2: String = "t2"
        val message2: String = "m2"
        val imageSource2: String = "img2"
        val imageAlt2: String = "alt2"
        val createdOn2: LocalDateTime = LocalDateTime.MAX
        val writer2: String = "w2"
        val email2: String = "e2"
        val new2 = New(id2, title2, message2, imageSource2, imageAlt2, createdOn2, writer2, email2)

        with(new1){
            assertEquals(id, id1)
            assertEquals(title, title1)
            assertEquals(message, message1)
            assertEquals(imageSource, imageSource1)
            assertEquals(imageAlt, imageAlt1)
            assertEquals(createdOn, createdOn1)
            assertEquals(writer, writer1)
            assertEquals(email, email1)
        }
        with(new2){
            assertEquals(id, id2)
            assertEquals(title, title2)
            assertEquals(message, message2)
            assertEquals(imageSource, imageSource2)
            assertEquals(imageAlt, imageAlt2)
            assertEquals(createdOn, createdOn2)
            assertEquals(writer, writer2)
            assertEquals(email, email2)
        }
    }
}