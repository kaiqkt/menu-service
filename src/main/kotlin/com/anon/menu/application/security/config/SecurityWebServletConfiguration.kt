package com.anon.menu.application.security.config

import com.anon.menu.application.security.filter.AuthenticationFilter
import com.anon.menu.application.security.filter.RestAuthenticationEntryPoint
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    @Value("\${auth-signing-secret}")
    private val secret: String,
    @Lazy
    @Qualifier("customAuthenticationManager")
    private val manager: AuthenticationManager,
    private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint
) {
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers(HttpMethod.POST, *POST_MATCHERS)
                .requestMatchers(HttpMethod.GET, *GET_MATCHERS)
                .requestMatchers(*PATH_MATCHERS)
        }
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .csrf { csrf ->
                csrf.disable()
            }
            .headers { headers ->
                headers.disable()
            }
            .authorizeHttpRequests { authorizeHttp ->
                authorizeHttp.anyRequest().authenticated()
            }
            .addFilter(AuthenticationFilter(manager, secret, restAuthenticationEntryPoint))
            .build()
    }

    companion object {
        private val POST_MATCHERS = arrayOf("/user", "/login")
        private val GET_MATCHERS = arrayOf("/restaurant/**")
        private val PATH_MATCHERS = arrayOf(
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/index.html",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/api-docs.yml"
        )
    }
}