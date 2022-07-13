package com.marina.surfgallery.auth.domain.entity

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
    val isBlank: Boolean = false
)