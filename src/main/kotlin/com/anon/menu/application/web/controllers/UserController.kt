package com.anon.menu.application.web.controllers

import com.anon.menu.application.security.AUTHORIZE_USER
import com.anon.menu.application.security.getUserId
import com.anon.menu.application.web.dto.toDomain
import com.anon.menu.application.web.dto.toV1
import com.anon.menu.domain.services.UserService
import com.anon.menu.generated.application.web.controllers.UserApi
import com.anon.menu.generated.application.web.dto.UserResponseV1
import com.anon.menu.generated.application.web.dto.UserV1
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) : UserApi {

    override fun create(userV1: UserV1): ResponseEntity<Unit> {
        userService.create(userV1.toDomain())
        return ResponseEntity.noContent().build()
    }

    @PreAuthorize(AUTHORIZE_USER)
    override fun find(): ResponseEntity<UserResponseV1> {
        val user = userService.findById(getUserId())

        return ResponseEntity.ok(user.toV1())
    }
}