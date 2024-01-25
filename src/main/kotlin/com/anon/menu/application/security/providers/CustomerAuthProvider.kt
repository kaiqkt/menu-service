package com.anon.menu.application.security.providers

import JWTUtils
import com.anon.menu.application.security.authentication.CustomAuthentication
import com.anon.menu.application.exceptions.JwtExpiredException
import com.anon.menu.application.security.filter.BEARER_PREFIX
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

class CustomerAuthProvider(private val secret: String) {

    fun handleCustomerAuth(authentication: CustomAuthentication): Authentication {
        val accessToken = (authentication.credentials as String).replace(BEARER_PREFIX, "")

        val token = try {
            JWTUtils.getClaims(accessToken, secret)
        } catch (ex: Exception) {
            throw JwtExpiredException()
        }

        authentication.userId = token.id
        token.authorities.map { authentication.authorities.add(SimpleGrantedAuthority(it)) }
        authentication.isAuthenticated = true

        return authentication
    }
}