package com.anon.menu.application.web.dto

import com.anon.menu.domain.entities.Category
import com.anon.menu.domain.entities.Restaurant
import com.anon.menu.generated.application.web.dto.CategoryResponseV1
import com.anon.menu.generated.application.web.dto.CategoryV1

fun Category.toV1() = CategoryResponseV1(
    id = this.id,
    name = this.name
)

fun CategoryV1.toDomain(restaurant: Restaurant) = Category(
    name = this.name,
    restaurant = restaurant
)