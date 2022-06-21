package ar.edu.unq.pds03backend.service

import ar.edu.unq.pds03backend.model.User
import io.jsonwebtoken.Claims
import java.util.Date


interface IJwtService {
    fun generateToken(user: User): String
    fun getExpirationDateFromToken(token: String): Date
    fun isTokenExpired(token: String): Boolean
    fun getUserIdFromToken(token: String): String
    fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T
}