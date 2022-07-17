package com.marina.surfgallery.auth.data.mappers

import com.marina.surfgallery.auth.data.remote.entity.response.UserDto
import com.marina.surfgallery.common.UserInfo

/*
* Маппер data -> domain
* */
fun UserDto.toUserInfo(token: String): UserInfo {
    return UserInfo(
        id = id,
        firstName = firstName,
        lastName = lastName,
        quote = about,
        photo = avatar,
        city = city,
        email = email,
        phone = phone,
        token = token
    )
}