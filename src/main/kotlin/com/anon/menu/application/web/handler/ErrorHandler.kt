package com.anon.menu.application.web.handler

import com.anon.menu.domain.exceptions.BadCredentialsException
import com.anon.menu.domain.exceptions.DomainException
import com.anon.menu.domain.exceptions.EmailInUseException
import com.anon.menu.domain.exceptions.UserNotFoundException
import com.anon.menu.generated.application.web.dto.ErrorV1
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(ex: DomainException): ResponseEntity<ErrorV1> {
        val error = ErrorV1(ex.type.name, ex.message)

        logger.info("Error: $error")

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException): ResponseEntity<ErrorV1> {
        val error = ErrorV1(ex.type.name, ex.message)

        logger.info("Error: $error")

        return ResponseEntity(error, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(EmailInUseException::class)
    fun handleEmailInUseException(ex: EmailInUseException): ResponseEntity<ErrorV1> {
        val error = ErrorV1(ex.type.name, ex.message)

        logger.info("Error: $error")

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: UserNotFoundException): ResponseEntity<ErrorV1> {
        val error = ErrorV1(ex.type.name, ex.message)

        logger.info("Error: $error")

        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }
}