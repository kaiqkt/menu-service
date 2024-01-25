package com.anon.menu.domain.services

import com.anon.menu.domain.entities.User
import com.anon.menu.domain.exceptions.EmailInUseException
import com.anon.menu.domain.exceptions.UserNotFoundException
import com.anon.menu.domain.repositories.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val repository: UserRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun create(user: User) {
        verifyIfExistsByEmail(user.email)

        repository.save(user)

        logger.info("User ${user.id} created successfully")
    }

    fun findById(userId: String): User = repository.findById(userId).getOrNull() ?: throw UserNotFoundException()

    fun findByEmail(email: String): User = repository.findByEmail(email) ?: throw UserNotFoundException()

    //update
    //delete

    private fun verifyIfExistsByEmail(email: String) {
        if (repository.existsByEmail(email)) {
            throw EmailInUseException()
        }
    }
}