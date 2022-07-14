package com.marina.surfgallery.auth.data.remote.entity.response

import com.google.gson.annotations.SerializedName

/*
* Ответ /auth/login
* */
data class LoginResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user_info")
    val userDto: UserDto
)