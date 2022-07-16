package com.marina.surfgallery.profile.presentation.entity

data class User(
    val firstName: String,
    val lastName: String,
    val quote: String,
    val photo: String,
    val city: String,
    val email: String,
    val phone: String
)