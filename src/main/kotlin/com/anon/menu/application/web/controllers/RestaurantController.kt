package com.anon.menu.application.web.controllers

import com.anon.menu.application.security.AUTHORIZE_USER
import com.anon.menu.application.security.getUserId
import com.anon.menu.application.web.dto.toDomain
import com.anon.menu.application.web.dto.toV1
import com.anon.menu.domain.services.RestaurantService
import com.anon.menu.domain.services.UserService
import com.anon.menu.generated.application.web.controllers.RestaurantApi
import com.anon.menu.generated.application.web.dto.AuthenticationV1
import com.anon.menu.generated.application.web.dto.RestaurantResponseV1
import com.anon.menu.generated.application.web.dto.RestaurantV1
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@RestController
class RestaurantController(
    private val userService: UserService,
    private val restaurantService: RestaurantService
): RestaurantApi {
    @PreAuthorize(AUTHORIZE_USER)
    override fun create(restaurantV1: RestaurantV1): ResponseEntity<AuthenticationV1> {
        val user = userService.findById(getUserId())
        restaurantService.save(restaurantV1.toDomain(user))

        return ResponseEntity.noContent().build()
    }

    override fun findById(restaurantId: String): ResponseEntity<RestaurantResponseV1> {
        val restaurant = restaurantService.findById(restaurantId)

        return ResponseEntity.ok(restaurant.toV1())
    }
}