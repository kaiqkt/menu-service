package com.anon.menu.application.security.filter

import com.anon.menu.application.exceptions.Error
import com.anon.menu.application.exceptions.ErrorType
import com.anon.menu.application.exceptions.JwtExpiredException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component("restAuthenticationEntryPoint")
class RestAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException
    ) {
        response.contentType = "application/vnd.kaiqkt_error_v1+json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        when (authenticationException) {
            is JwtExpiredException -> {
                val error = Error(ErrorType.ACCESS_TOKEN_EXPIRED, "Access token expired")
                response.outputStream.println(jacksonObjectMapper().writeValueAsString(error))
            }

            else -> {
                val error = Error(
                    ErrorType.UNKNOWN,
                    authenticationException.message ?: "Unknown error in spring security filter"
                )
                response.outputStream.println(jacksonObjectMapper().writeValueAsString(error))
            }
        }
    }
}
