package com.anon.menu.application.security.authentication

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class CustomAuthentication(private val accessToken: String) : Authentication {
    private var authenticated: Boolean = false
    private val grantedAuthority = mutableListOf<GrantedAuthority>()
    lateinit var userId: String

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }

    override fun isAuthenticated(): Boolean = this.authenticated

    override fun getDetails(): Any = "UNKNOWN"

    override fun getName(): String = "AUTHENTICATION_TOKEN"

    override fun getAuthorities(): MutableCollection<GrantedAuthority> = grantedAuthority

    override fun getCredentials(): Any = accessToken

    override fun getPrincipal(): Any = accessToken
}