package com.marina.surfgallery.core.presentation.entity

data class PictureItem(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: Long,
    var isFavorite: Boolean
)