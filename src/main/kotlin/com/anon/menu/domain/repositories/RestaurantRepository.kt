package com.anon.menu.domain.repositories

import com.anon.menu.domain.entities.Restaurant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository : JpaRepository<Restaurant, String> {
    fun findAllByUserId(userId: String): List<Restaurant>
}
