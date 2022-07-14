package ar.edu.unq.pds03backend.service


import ar.edu.unq.pds03backend.service.email.impl.EmailService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import java.io.File

class EmailServiceTest {
    companion object {
        const val USER_FROM = "pepe@outlook.com"
        const val SLEEP_TIME_MS: Long = 0
    }
    @RelaxedMockK
    private lateinit var emailSender: JavaMailSender

    private lateinit var emailService: EmailService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        emailService = EmailService(emailSender= emailSender, from = USER_FROM, sleepTimeMs = SLEEP_TIME_MS)
    }

    @Test
    fun `test send email not throws exception if emailSender not throws`(){
        val to = "pepe@gmail.com"
        val subject = "subject"
        val text = "text  .... cogito ergo sum ...."
        val message = SimpleMailMessage()
        message.setFrom(USER_FROM)
        message.setTo(to)
        message.setSubject(subject)
        message.setText(text)
        every { emailSender.send(message) } returns Unit

        emailService.sendEmail(to, subject, text)

        verify(exactly = 1) { emailSender.send(message) }
    }

    @Test
    fun `test send message with attachment`(){
        val to = "asd"
        val subject = "pepe"
        val text = "some text"
        val pathToAttachment = "path to attachment"
        val message = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setFrom(USER_FROM)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(text)
        val file = FileSystemResource(File(pathToAttachment))
        helper.addAttachment("Invoice", file)
        every { emailSender.send(message) } returns Unit
        emailService.sendMessageWithAttachment(to, subject, text, pathToAttachment)
        verify(exactly = 1) { emailSender.send(message) }
    }
}