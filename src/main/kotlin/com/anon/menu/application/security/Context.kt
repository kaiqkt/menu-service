package com.anon.menu.application.security

import com.anon.menu.application.security.authentication.CustomAuthentication
import org.springframework.security.core.context.SecurityContextHolder

fun getUserId() = (SecurityContextHolder.getContext().authentication as CustomAuthentication).userId
