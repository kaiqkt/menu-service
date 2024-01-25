package com.anon.menu.application.web.dto

import com.anon.menu.domain.entities.Authentication
import com.anon.menu.generated.application.web.dto.AuthenticationV1

fun Authentication.toV1() = AuthenticationV1(
    userId = this.userId,
    name = this.name,
    accessToken = this.accessToken
)