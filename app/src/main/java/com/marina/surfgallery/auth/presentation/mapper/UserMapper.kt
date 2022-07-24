package com.marina.surfgallery.profile.presentation.mapper

import com.marina.surfgallery.common.entity.UserInfo
import com.marina.surfgallery.auth.presentation.entity.User

fun UserInfo.toUser(): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        quote = quote,
        city = city,
        phone = phone,
        photo = photo,
        email = email
    )
}