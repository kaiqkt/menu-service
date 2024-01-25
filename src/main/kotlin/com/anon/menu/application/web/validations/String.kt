package com.anon.menu.application.web.validations

import com.anon.menu.application.exceptions.ErrorType
import com.anon.menu.application.exceptions.InvalidFieldException

private const val EMAIL_REGEX = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}"
private const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$"
private const val PHONE_REGEX = """^\d{10,11}$"""

fun String.isValidEmail() = this.apply {
    val regex = EMAIL_REGEX.toRegex()

    if (regex.matches(this)) {
        return@apply
    }

    throw InvalidFieldException(ErrorType.INVALID_EMAIL, "Invalid Email")
}

fun String.isValidPassword() = this.apply {
    val regex = PASSWORD_REGEX.toRegex()

    if (regex.matches(this)) {
        return@apply
    }

    throw InvalidFieldException(ErrorType.INVALID_PASSWORD, "Password does not meet the minimum requirements")
}

fun String.isValidPhone() = this.apply {
    val regex = PHONE_REGEX.toRegex()

    if (regex.matches(this)) {
        return@apply
    }

    throw InvalidFieldException(ErrorType.INVALID_PHONE, "Invalid Phone")
}

