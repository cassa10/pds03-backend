package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.exception.UserNotFoundException
import ar.edu.unq.pds03backend.model.User
import ar.edu.unq.pds03backend.repository.IUserRepository
import ar.edu.unq.pds03backend.service.IJwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*

@Component
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String? = null,
    @Autowired private val userRepository: IUserRepository
): IJwtService, Serializable {

    companion object {
        private const val serialVersionUID = -2550185165626007488L
        const val JWT_TOKEN_VALIDITY = Int.MAX_VALUE
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

    fun getDniFromToken(token: String): String {
        val id = getUserIdFromToken(token)
        val user = userRepository.findById(id.toLong())
        if (!user.isPresent) throw UserNotFoundException()

        return user.get().dni
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

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username: String = getDniFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun doGenerateToken(userId: String): String {
        return Jwts.builder()
                .setIssuer(userId)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact()
    }
}