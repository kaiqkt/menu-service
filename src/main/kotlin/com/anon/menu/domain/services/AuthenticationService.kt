package com.anon.menu.domain.services

import JWTUtils
import com.anon.menu.domain.entities.Authentication
import com.anon.menu.domain.exceptions.BadCredentialsException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import kotlin.math.log

@Service
class AuthenticationService(
    private val userService: UserService,
    @Value("\${auth-signing-secret}")
    private val secret: String,
    @Value("\${access-token-expiration}")
    private val accessTokenExpiration: String
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun authenticate(email: String, password: String): Authentication {
        val user = userService.findByEmail(email)

        if (!BCrypt.checkpw(password, user.password)) {
            throw BadCredentialsException()
        }

        logger.info("User ${user.id} authenticated successfully")

        return Authentication(
            userId = user.id,
            name = user.name,
            accessToken = JWTUtils.generate(user, secret, accessTokenExpiration)
        )
    }
}