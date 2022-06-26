package ar.edu.unq.pds03backend.service.email

interface IEmailService {
    fun sendEmail(to:String, subject:String, text:String)
    fun sendMessageWithAttachment(to:String, subject:String, text:String, pathToAttachment:String)
}