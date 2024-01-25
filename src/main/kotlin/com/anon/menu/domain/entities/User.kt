package com.anon.menu.domain.entities

import io.azam.ulidj.ULID
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "menu_user")
data class User(
    @Id
    val id: String = ULID.random(),
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String,
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val restaurant: List<Restaurant> = listOf()
)
