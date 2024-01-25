package com.anon.menu.domain.exceptions

class BadCredentialsException : DomainException(ErrorType.INCORRECT_PASSWORD, "Incorrect password")
