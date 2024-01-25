package com.anon.menu.domain.entities

import io.azam.ulidj.ULID
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.math.BigInteger
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity
data class Restaurant(
    @Id
    val id: String = ULID.random(),
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val deliveryFee: BigDecimal,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    val user: User,
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val categories: List<Category> = listOf(),
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val items: List<Item> = listOf(),
)
