package ar.edu.unq.pds03backend.service.email.impl

import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.service.email.IEmailSender
import ar.edu.unq.pds03backend.service.email.IEmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmailSender(@Autowired private val emailService: IEmailService): IEmailSender {
    companion object {
        const val sendNewPasswordToUserSubject = "Nueva contraseña"
    }

    override fun sendNewPasswordMailToUser(user: User, plainPassword: String) {
        emailService.sendEmail(
            user.email,
            addNoReply(sendNewPasswordToUserSubject),
            "Este es un mensaje automático.\n\nNueva contraseña generada: ${plainPassword}\n\n\nPara ingresar en el sistema y empezar a solicitar cupos, ingrese su dni y su nueva contraseña generada.\n\n\nPor favor, no responda el presente mail ni lo comparta!"
        )
    }

    private fun addNoReply(str:String):String = "[No Reply] - $str"
}