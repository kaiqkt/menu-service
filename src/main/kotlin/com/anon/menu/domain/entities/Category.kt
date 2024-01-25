package com.anon.menu.domain.entities

import io.azam.ulidj.ULID
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
data class Category(
    @Id
    val id: String = ULID.random(),
    val image: String,
    val name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
    var restaurant: Restaurant
)
