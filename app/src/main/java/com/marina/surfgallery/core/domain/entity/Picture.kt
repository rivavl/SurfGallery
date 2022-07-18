package com.marina.surfgallery.core.domain.entity

data class Picture(
    val id: String,
    var title: String,
    var content: String,
    var photoUrl: String,
    val publicationDate: Long,
    var isFavorite: Boolean
)