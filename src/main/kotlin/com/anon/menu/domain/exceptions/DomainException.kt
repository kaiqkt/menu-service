package com.anon.menu.domain.exceptions

open class DomainException(
    open val type: ErrorType,
    override val message: String
) : Exception(message)
