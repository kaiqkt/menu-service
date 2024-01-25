package com.anon.menu.domain.exceptions

class RestaurantNotFoundException : DomainException(ErrorType.RESTAURANT_NOT_FOUND, "Restaurant not found")
