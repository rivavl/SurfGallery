package com.marina.surfgallery.auth.domain.use_case

import com.marina.surfgallery.auth.domain.entity.ValidationResult
import com.marina.surfgallery.auth.domain.util.PASSWORD_REGEX

class ValidatePasswordUseCase {

    operator fun invoke(password: String): ValidationResult {

        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Поле не может быть пустым",
                isBlank = true
            )
        }

        if (!PASSWORD_REGEX.toRegex().matches(password)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Неверный пароль",
                isBlank = false
            )
        }

        return ValidationResult(
            successful = true
        )
    }

}