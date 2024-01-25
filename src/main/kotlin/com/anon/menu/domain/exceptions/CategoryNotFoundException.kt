package com.anon.menu.domain.exceptions

class CategoryNotFoundException : DomainException(ErrorType.CATEGORY_NOT_FOUND, "Category not found")
