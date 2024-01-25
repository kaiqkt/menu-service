package com.anon.menu.domain.services

import com.anon.menu.domain.entities.Item
import com.anon.menu.domain.repositories.ItemRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ItemService(
    private val repository: ItemRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun save(item: Item) {
        repository.save(item)

        logger.info("Item ${item.id} for restaurant ${item.restaurant.id} created successfully")
    }

    fun findById(id: String): Item? {
        return repository.findById(id).getOrNull()
    }

    //update

    fun deleteById(id: String) {
        repository.deleteById(id)

        logger.info("Item $id deleted successfully")
    }
}
