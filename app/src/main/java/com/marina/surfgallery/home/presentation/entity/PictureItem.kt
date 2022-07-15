package com.marina.surfgallery.home.presentation.entity

data class PictureItem(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: Long
)