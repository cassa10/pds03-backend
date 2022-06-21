package ar.edu.unq.pds03backend

import ar.edu.unq.pds03backend.exception.UserNotFoundException
import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.repository.IQuoteRequestRepository
import ar.edu.unq.pds03backend.repository.ISemesterRepository
import ar.edu.unq.pds03backend.repository.IUserRepository
import ar.edu.unq.pds03backend.service.IUserService
import ar.edu.unq.pds03backend.service.impl.UserService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.util.*

private const val USER_ID: Long = 1

class UserServiceTest {

    @RelaxedMockK
    private lateinit var userRepository: IUserRepository

    @RelaxedMockK
    private lateinit var quoteRequestRepository: IQuoteRequestRepository

    @RelaxedMockK
    private lateinit var semesterRepository: ISemesterRepository

    private lateinit var userService: IUserService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        userService = UserService(userRepository, quoteRequestRepository, semesterRepository)
    }

    @Test(expected = UserNotFoundException::class)
    fun `given a user not found when call getById then it should throw UserNotFoundException`() {
        val optionalUserMock = mockk<Optional<User>> {
            every { isPresent } returns false
        }
        every { userRepository.findById(any()) } returns optionalUserMock

        userService.getById(USER_ID)
    }

    @Test
    fun `given a user id when call getById then it should UserResponseDTO`() {

    }
}
