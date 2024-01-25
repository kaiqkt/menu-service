package com.anon.menu.domain.entities


data class Authentication(
    val userId: String,
    val name: String,
    val accessToken: String
)
