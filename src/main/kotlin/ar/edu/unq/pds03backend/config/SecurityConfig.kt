package ar.edu.unq.pds03backend.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
class SecurityConfig(
        @Lazy private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
        @Lazy private val jwtRequestFilter: JwtRequestFilter,
        @Lazy private val environment: Environment
) : WebSecurityConfigurerAdapter(), WebMvcConfigurer {

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .deny()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/swagger-ui.html/**",
                        "/configuration/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/api/v1/login",
                        "/api/v2/login",
                ).permitAll()
                .antMatchers(
                        "/api/v1/quoteRequest/{id}/accept",
                        "/api/v1/quoteRequest/{id}/revoke",
                        "/api/v1/quoteRequest/{id}/rollback",
                        "/api/v1/quoteRequest/{id}/adminComment",
                        "/**/import",
                        "/api/v1/user/register/student/massive",
                        "/api/v1/user/student/enrolled/courses/current",
                ).hasAnyAuthority("DIRECTOR")
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:" + environment.getProperty("location.files"))
    }
}
