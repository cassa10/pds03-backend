package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.model.Student
import ar.edu.unq.pds03backend.service.email.IEmailSender
import ar.edu.unq.pds03backend.service.email.IEmailService
import ar.edu.unq.pds03backend.service.email.impl.EmailSender
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class EmailSenderTest {

    private lateinit var emailSender: IEmailSender

    @RelaxedMockK
    private lateinit var emailService: IEmailService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        emailSender = EmailSender(emailService = emailService)
    }

    @Test
    fun `test email sender send new password mail to user`(){
        val plainPassword = "123456"
        val emailUser = "email user"
        val user = Student.Builder().withEmail(emailUser).build()
        val subject = "[No Reply] - ${EmailSender.sendNewPasswordToUserSubject}"
        val text = "Este es un mensaje automático.\n\nNueva contraseña generada: ${plainPassword}\n\n\nPara ingresar en el sistema y empezar a solicitar cupos, ingrese su dni y su nueva contraseña generada.\n\n\nPor favor, no responda el presente mail ni lo comparta!"
        every { emailService.sendEmail(emailUser, subject, text) } returns Unit
        emailSender.sendNewPasswordMailToUser(user, plainPassword)

    }
}