package com.anon.menu.domain.exceptions

class UserNotFoundException : DomainException(ErrorType.USER_NOT_FOUND, "User not found")
