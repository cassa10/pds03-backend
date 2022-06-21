package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.service.IJwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*

@Component
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String? = null
): IJwtService, Serializable {

    companion object {
        private const val serialVersionUID = -2550185165626007488L
        private const val hour = 60 * 60 * 1000
        const val JWT_TOKEN_VALIDITY = 48 * hour
    }

    override fun generateToken(user: User): String {
        return doGenerateToken(user.id.toString())
    }

    override fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver(claims)
    }

    override fun getUserIdFromToken(token: String): String {
        return getClaimFromToken(token) { it.issuer }
    }

    override fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token) { it.expiration }
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    override fun isTokenExpired(token: String): Boolean {
        val expiration: Date = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    private fun doGenerateToken(userId: String): String {
        return Jwts.builder()
                .setIssuer(userId)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact()
    }
}