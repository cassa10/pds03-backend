package ar.edu.unq.pds03backend.service

interface IPasswordService {
    fun generatePassword(): String
    fun encryptPassword(password: String): String
    fun matches(plainPassword: String, password: String): Boolean
}