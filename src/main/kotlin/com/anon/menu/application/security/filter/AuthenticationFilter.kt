package com.anon.menu.application.security.filter

import com.anon.menu.application.security.authentication.CustomAuthentication
import com.anon.menu.application.security.providers.CustomerAuthProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

const val BEARER_PREFIX = "Bearer "

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val secret: String,
    private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint
) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val accessTokenHeader = request.getHeader(AUTHORIZATION)

        if (accessTokenHeader.isNullOrBlank()) {
            chain.doFilter(request, response)
        } else {
            try {
                val customerAuth = CustomAuthentication(accessTokenHeader)

                if (accessTokenHeader.startsWith(BEARER_PREFIX)) {
                    val authentication = CustomerAuthProvider(secret).handleCustomerAuth(customerAuth)

                    SecurityContextHolder.getContext().authentication = authentication
                    onSuccessfulAuthentication(request, response, authentication)
                    chain.doFilter(request, response)
                }
            } catch (e: AuthenticationException) {
                SecurityContextHolder.clearContext()
                onUnsuccessfulAuthentication(request, response, e)
                restAuthenticationEntryPoint.commence(request, response, e)
            }
        }
    }
}