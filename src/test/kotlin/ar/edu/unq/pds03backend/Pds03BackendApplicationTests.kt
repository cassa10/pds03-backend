package ar.edu.unq.pds03backend

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(locations = ["classpath:application-test.properties"])
class Pds03BackendApplicationTests {

	@Test
	fun contextLoads() {
	}

}
