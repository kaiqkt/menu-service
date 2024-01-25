package com.anon.menu.domain.services

import com.anon.menu.domain.entities.Category
import com.anon.menu.domain.entities.Restaurant
import com.anon.menu.domain.repositories.CategoryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CategoryService(
    private val repository: CategoryRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun save(category: Category) {
        repository.save(category)

        logger.info("Category ${category.id} for restaurant ${category.restaurant.id} created successfully")
    }

    fun findById(id: String) : Category? {
        return repository.findById(id).getOrNull()
    }

    //update

    fun deleteById(id: String) {
        repository.deleteById(id)

        logger.info("Category $id deleted successfully")
    }
}

