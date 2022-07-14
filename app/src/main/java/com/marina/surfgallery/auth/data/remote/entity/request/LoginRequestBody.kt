package com.marina.surfgallery.auth.data.remote.entity.request

/*
* Тело запроса авторизации
* */
data class LoginRequestBody(
    val password: String,
    val phone: String
)