package com.marina.surfgallery.auth.domain.use_case.validation

import com.marina.surfgallery.auth.domain.entity.validation.ValidationResult
import com.marina.surfgallery.auth.domain.util.PHONE_NUMBER_REGEX

class ValidateLoginUseCase {

    operator fun invoke(phoneNumber: String): ValidationResult {

        if (phoneNumber.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Поле не может быть пустым",
                isBlank = true
            )
        }

        if (!PHONE_NUMBER_REGEX.toRegex().matches(phoneNumber)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Неверный логин",
                isBlank = false
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}