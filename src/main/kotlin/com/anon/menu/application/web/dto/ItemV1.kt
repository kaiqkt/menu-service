package com.anon.menu.application.web.dto

import com.anon.menu.domain.entities.Category
import com.anon.menu.domain.entities.Item
import com.anon.menu.domain.entities.Restaurant
import com.anon.menu.generated.application.web.dto.ItemResponseV1
import com.anon.menu.generated.application.web.dto.ItemV1

fun Item.toV1() = ItemResponseV1(
    name = this.name,
    description = this.description,
    price = this.price,
    category = this.category?.toV1(),
)

fun ItemV1.toDomain(category: Category?, restaurant: Restaurant) = Item(
    name = this.name,
    description = this.description,
    price = this.price,
    category = category,
    restaurant = restaurant
)
