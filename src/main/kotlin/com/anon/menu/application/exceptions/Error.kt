package com.anon.menu.application.exceptions

data class Error(
    val type: ErrorType,
    val message: String
)