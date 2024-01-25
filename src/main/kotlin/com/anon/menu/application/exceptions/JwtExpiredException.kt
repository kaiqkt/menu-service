package com.anon.menu.application.exceptions

import org.springframework.security.core.AuthenticationException

class JwtExpiredException: AuthenticationException("Access token expired")