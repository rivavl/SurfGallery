package com.marina.surfgallery.common

data class UserInfo(
    val id: String,
    val firstName: String,
    val lastName: String,
    val quote: String,
    val photo: String,
    val city: String,
    val email: String,
    val phone: String,
    val token: String
)