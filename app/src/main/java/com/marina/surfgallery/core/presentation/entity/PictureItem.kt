package com.marina.surfgallery.core.presentation.entity

import java.io.Serializable

data class PictureItem(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: String,
    var isFavorite: Boolean
) : Serializable