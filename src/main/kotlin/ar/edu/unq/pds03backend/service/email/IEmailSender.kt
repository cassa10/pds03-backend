package ar.edu.unq.pds03backend.service.email

import ar.edu.unq.pds03backend.model.User

interface IEmailSender {
    fun sendNewPasswordMailToUser(user: User, plainPassword: String)
}