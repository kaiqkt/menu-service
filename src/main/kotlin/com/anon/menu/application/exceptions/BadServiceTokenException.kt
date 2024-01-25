package com.anon.menu.application.exceptions

import org.springframework.security.core.AuthenticationException

class BadServiceTokenException: AuthenticationException("Service secret invalid")