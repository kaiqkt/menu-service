package com.anon.menu.application.web.controllers

import com.anon.menu.application.security.AUTHORIZE_USER
import com.anon.menu.application.security.getUserId
import com.anon.menu.application.web.dto.toDomain
import com.anon.menu.domain.entities.Category
import com.anon.menu.domain.services.CategoryService
import com.anon.menu.domain.services.ItemService
import com.anon.menu.domain.services.RestaurantService
import com.anon.menu.generated.application.web.controllers.ItemApi
import com.anon.menu.generated.application.web.dto.ItemV1
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemController(
    private val itemService: ItemService,
    private val categoryService: CategoryService,
    private val restaurantService: RestaurantService
) : ItemApi {
    @PreAuthorize(AUTHORIZE_USER)
    override fun create(restaurantId: String, itemV1: ItemV1): ResponseEntity<Unit> {
        val restaurant = restaurantService.findByIdAndUserId(restaurantId, getUserId())
        val category = itemV1.categoryId?.let { categoryService.findByIdAndRestaurantId(it, restaurantId) }
        val item = itemV1.toDomain(category, restaurant)

        itemService.save(item)

        return ResponseEntity.noContent().build()
    }
}