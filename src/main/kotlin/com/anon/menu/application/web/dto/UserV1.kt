package com.anon.menu.application.web.dto

import com.anon.menu.application.web.validations.isValidEmail
import com.anon.menu.application.web.validations.isValidPassword
import com.anon.menu.application.web.validations.isValidPhone
import com.anon.menu.domain.entities.Role
import com.anon.menu.domain.entities.User
import com.anon.menu.generated.application.web.dto.UserResponseV1
import com.anon.menu.generated.application.web.dto.UserV1
import org.springframework.security.crypto.bcrypt.BCrypt

fun UserV1.toDomain(): User = User(
    name = this.name,
    email = this.email.isValidEmail(),
    phone = this.phone.isValidPhone(),
    role = Role.ROLE_USER.name,
    password = BCrypt.hashpw(this.password.isValidPassword(), BCrypt.gensalt())
)


fun User.toV1(): UserResponseV1 = UserResponseV1(
    name = this.name,
    email = this.email,
    phone = this.phone
)