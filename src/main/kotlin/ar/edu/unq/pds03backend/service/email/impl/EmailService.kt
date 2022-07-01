package ar.edu.unq.pds03backend.service.email.impl

import ar.edu.unq.pds03backend.service.email.IEmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.File
import java.lang.Thread.sleep
import java.util.*

@Service
class EmailService(
    @Autowired private val emailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    private val from: String,
    @Value("\${mail.sleep_time}")
    private val sleepTimeMs: Long,
): IEmailService {
    companion object {
        const val MAX_VALUE_OF_RANDOM = 5000
    }
    @Async("threadPoolTaskExecutor")
    override fun sendEmail(to: String, subject: String, text: String) {
        sleep(getSleepEmail())
        val message = SimpleMailMessage()
        message.setFrom(from)
        message.setTo(to)
        message.setSubject(subject)
        message.setText(text)
        emailSender.send(message)
    }

    @Async("threadPoolTaskExecutor")
    override fun sendMessageWithAttachment(to: String, subject: String, text: String, pathToAttachment: String) {
        sleep(getSleepEmail())
        val message = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setFrom(from)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(text)
        val file = FileSystemResource(File(pathToAttachment))
        helper.addAttachment("Invoice", file)
        emailSender.send(message)
    }

    private fun getSleepEmail(): Long = sleepTimeMs + getRandom()
    //Uses random decrease concurrent exception from smtp server
    private fun getRandom(): Long =
        Random(System.nanoTime()).nextInt(MAX_VALUE_OF_RANDOM).toLong()

}