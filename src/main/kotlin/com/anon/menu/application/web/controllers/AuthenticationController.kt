package com.anon.menu.application.web.controllers

import com.anon.menu.application.web.dto.toV1
import com.anon.menu.domain.services.AuthenticationService
import com.anon.menu.generated.application.web.controllers.LoginApi
import com.anon.menu.generated.application.web.dto.AuthenticationV1
import com.anon.menu.generated.application.web.dto.LoginV1
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
    private val authenticationService: AuthenticationService
) : LoginApi {

    override fun authenticate(loginV1: LoginV1): ResponseEntity<AuthenticationV1> {
        val authentication = authenticationService.authenticate(loginV1.email, loginV1.password)

        return ResponseEntity.ok(authentication.toV1())
    }
}