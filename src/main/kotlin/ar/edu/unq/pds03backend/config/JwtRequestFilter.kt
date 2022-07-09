package ar.edu.unq.pds03backend.config

import ar.edu.unq.pds03backend.service.impl.JwtService
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.beans.factory.annotation.Autowired
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.FilterChain
import java.lang.IllegalArgumentException
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component

@Component
class JwtRequestFilter(
        @Autowired private val jwtService: JwtService,
        @Autowired private val jwtUserDetailsService: JwtUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse, chain: FilterChain) {
        val requestTokenHeader = request.getHeader("Authorization")
        var username: String? = null
        var jwtToken: String? = null
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                username = jwtService.getDniFromToken(jwtToken)
            } catch (e: IllegalArgumentException) {
                println("Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {
                println("JWT Token has expired")
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String")
        }
        if (username != null &&
                SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = jwtUserDetailsService.loadUserByUsername(username)
            if (jwtService.validateToken(jwtToken!!, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }
}
