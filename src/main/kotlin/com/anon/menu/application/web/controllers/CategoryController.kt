package com.anon.menu.application.web.controllers

import com.anon.menu.application.security.AUTHORIZE_USER
import com.anon.menu.application.security.getUserId
import com.anon.menu.application.web.dto.toDomain
import com.anon.menu.application.web.dto.toV1
import com.anon.menu.domain.services.CategoryService
import com.anon.menu.domain.services.RestaurantService
import com.anon.menu.generated.application.web.controllers.CategoryApi
import com.anon.menu.generated.application.web.dto.CategoryResponseV1
import com.anon.menu.generated.application.web.dto.CategoryV1
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController(
    private val categoryService: CategoryService,
    private val restaurantService: RestaurantService
): CategoryApi{

    @PreAuthorize(AUTHORIZE_USER)
    override fun create(restaurantId: String, categoryV1: CategoryV1): ResponseEntity<Unit> {
        val restaurant = restaurantService.findByIdAndUserId(restaurantId, getUserId())
        val category = categoryV1.toDomain(restaurant)
        categoryService.save(category)
        return ResponseEntity.noContent().build()
    }

    @PreAuthorize(AUTHORIZE_USER)
    override fun findAll(restaurantId: String): ResponseEntity<List<CategoryResponseV1>> {
        val categories = categoryService.findAllByRestaurantId(restaurantId).map { it.toV1() }

        return ResponseEntity.ok(categories)
    }
}