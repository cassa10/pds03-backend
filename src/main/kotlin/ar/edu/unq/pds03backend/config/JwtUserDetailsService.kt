package ar.edu.unq.pds03backend.config

import ar.edu.unq.pds03backend.model.Role
import ar.edu.unq.pds03backend.service.impl.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User

@Service
class JwtUserDetailsService(
        @Autowired private val userService: UserService
): UserDetailsService {

    override fun loadUserByUsername(dni: String): UserDetails {
        val user = userService.findByDni(dni)

        if (!user.isPresent) throw UsernameNotFoundException("User not found with dni: $dni")

        return with(user.get()) { User(dni, password, mapAuthorities(role())) }
    }

    private fun mapAuthorities(role: Role): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }
}
