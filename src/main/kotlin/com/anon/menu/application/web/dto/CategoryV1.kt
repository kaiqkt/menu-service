package com.anon.menu.application.web.dto

import com.anon.menu.domain.entities.Category
import com.anon.menu.generated.application.web.dto.CategoryV1

fun Category.toV1() = CategoryV1(
    name = this.name
)