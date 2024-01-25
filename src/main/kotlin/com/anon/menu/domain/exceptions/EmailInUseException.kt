package com.anon.menu.domain.exceptions

class EmailInUseException : DomainException(ErrorType.EMAIL_IN_USE, "Email already in use")
