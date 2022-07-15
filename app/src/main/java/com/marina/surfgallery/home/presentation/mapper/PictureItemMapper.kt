package com.marina.surfgallery.home.presentation.mapper

import com.marina.surfgallery.home.domain.entity.Picture
import com.marina.surfgallery.home.presentation.entity.PictureItem

fun List<Picture>.toPresentation(): List<PictureItem> {
    return map {
        PictureItem(
            id = it.id,
            title = it.title,
            content = it.content,
            photoUrl = it.photoUrl,
            publicationDate = it.publicationDate
        )
    }
}