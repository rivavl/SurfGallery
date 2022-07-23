package com.marina.surfgallery.auth.domain.entity.validation

/*
* Результат валидации для логина и пароля
* */
data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null,
    val isBlank: Boolean = false
)