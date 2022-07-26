package com.marina.surfgallery.auth.domain.use_case.validation

import com.marina.surfgallery.auth.domain.entity.validation.ValidationResult
import com.marina.surfgallery.auth.domain.util.PASSWORD_REGEX
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): ValidationResult {

        if (password.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Поле не может быть пустым",
                isBlank = true
            )
        }

        if (!PASSWORD_REGEX.toRegex().matches(password)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Неверный пароль",
                isBlank = false
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }

}