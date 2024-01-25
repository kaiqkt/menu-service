package com.anon.menu.application.exceptions

class InvalidFieldException(
    val type: ErrorType,
    override val message: String
) : Exception(message)
