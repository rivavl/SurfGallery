package com.marina.surfgallery.core.presentation.mapper

import com.marina.surfgallery.core.domain.entity.Picture
import com.marina.surfgallery.core.presentation.entity.PictureItem

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