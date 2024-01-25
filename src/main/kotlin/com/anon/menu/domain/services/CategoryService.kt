package com.anon.menu.domain.services

import com.anon.menu.domain.entities.Category
import com.anon.menu.domain.exceptions.CategoryNotFoundException
import com.anon.menu.domain.repositories.CategoryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val repository: CategoryRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun save(category: Category) {
        repository.save(category)

        logger.info("Category ${category.id} for restaurant ${category.restaurant.id} created successfully")
    }

    fun findByIdAndRestaurantId(id: String, restaurantId: String): Category? {
        return repository.findByIdAndRestaurantId(id, restaurantId) ?: throw CategoryNotFoundException()
    }

    fun findAllByRestaurantId(restaurantId: String): List<Category> {
        return repository.findAllByRestaurantId(restaurantId)
    }

    //update

    fun deleteById(id: String) {
        repository.deleteById(id)

        logger.info("Category $id deleted successfully")
    }
}

