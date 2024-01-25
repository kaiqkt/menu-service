package com.anon.menu.application.web.dto

import com.anon.menu.application.web.validations.isValidEmail
import com.anon.menu.application.web.validations.isValidPhone
import com.anon.menu.domain.entities.Restaurant
import com.anon.menu.domain.entities.User
import com.anon.menu.generated.application.web.dto.RestaurantResponseV1
import com.anon.menu.generated.application.web.dto.RestaurantV1

fun RestaurantV1.toDomain(user: User) = Restaurant(
    name = this.name,
    email = this.email.isValidEmail(),
    phone = this.phone.isValidPhone(),
    address = this.address,
    deliveryFee = this.deliveryFee,
    user = user
)

fun Restaurant.toV1() = RestaurantResponseV1(
    name = this.name,
    email = this.email,
    phone = this.phone,
    address = this.address,
    deliveryFee = this.deliveryFee,
    items = this.items.map { it.toV1() }.sortedBy { it.category?.name }
)