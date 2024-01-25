package com.anon.menu.domain.utils

import AUTHORITIES
import io.jsonwebtoken.Claims
import java.util.Date

data class TokenDecrypted(
    val id: String,
    val authorities: List<String>,
    val expireAt: Date
) {

    @Suppress("UNCHECKED_CAST")
    constructor(claims: Claims) : this(
        id = claims.subject,
        authorities = claims[AUTHORITIES] as List<String>,
        expireAt = claims.expiration
    )
}
