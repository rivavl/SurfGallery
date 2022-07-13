package com.marina.surfgallery.auth.presentation.entity

sealed class FieldsState(
    val login: String? = null,
    val password: String? = null,
    val message: String? = null
) {
    class Success(login: String? = null, password: String? = null) :
        FieldsState(login = login, password = password)

    class PasswordError(message: String) : FieldsState(message = message)
    class LoginError(message: String) : FieldsState(message = message)
}