package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.service.IPasswordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class PasswordService(
    @Autowired private val passwordEncoder: PasswordEncoder,
): IPasswordService {

    companion object {
        private const val PASSWORD_SIZE = 8
    }

    override fun encryptPassword(password: String): String =
        passwordEncoder.encode(password)

    override fun matches(plainPassword: String, password: String): Boolean =
        passwordEncoder.matches(plainPassword, password)


    override fun generatePassword(): String {
        val charSet = "0123456789!abcdefghijkl)%&mnopqrstuvwxyz#ABCDEFGHIJKLMNOPQRSTUVWXYZ=(+-_"
        val random = Random(System.nanoTime())
        val password = StringBuilder()
        for (i in 0 until PASSWORD_SIZE) {
            val ranIndex = random.nextInt(charSet.length)
            password.append(charSet[ranIndex])
        }
        return password.toString()
    }

}