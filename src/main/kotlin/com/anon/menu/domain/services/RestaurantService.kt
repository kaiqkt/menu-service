package com.anon.menu.domain.services

import com.anon.menu.domain.entities.Restaurant
import com.anon.menu.domain.exceptions.RestaurantNotFoundException
import com.anon.menu.domain.repositories.RestaurantRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class RestaurantService(
    private val repository: RestaurantRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun save(restaurant: Restaurant) {
        repository.save(restaurant)

        logger.info("Restaurant ${restaurant.id} for user ${restaurant.user.id} created successfully")
    }

    fun findAllByUserId(userId: String) : List<Restaurant> {
        return repository.findAllByUserId(userId)
    }

    fun findById(id: String): Restaurant {
        return repository.findById(id).getOrNull() ?: throw RestaurantNotFoundException()
    }

    //update

    fun deleteById(id: String) {
        repository.deleteById(id)

        logger.info("Restaurant $id deleted successfully")
    }
}
