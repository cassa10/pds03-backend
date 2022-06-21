package ar.edu.unq.pds03backend.config

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.
            csrf().disable().
            cors(withDefaults()).authorizeRequests().
            mvcMatchers(
                "/swagger-ui.html/**",
                "/configuration/**",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/webjars/**",
                "/**",
            ).permitAll()
            .mvcMatchers(HttpMethod.POST,
                "/**").permitAll()
            .mvcMatchers(HttpMethod.PUT,
                "/**").permitAll()
            .and()
            .httpBasic()
    }
}