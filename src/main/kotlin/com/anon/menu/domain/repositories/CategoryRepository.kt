package com.anon.menu.domain.repositories

import com.anon.menu.domain.entities.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, String>{
    fun findAllByRestaurantId(restaurantId: String): List<Category>
    fun findByIdAndRestaurantId(id: String, restaurantId: String): Category?
}
