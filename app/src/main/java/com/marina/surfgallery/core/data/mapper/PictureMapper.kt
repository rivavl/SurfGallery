package com.marina.surfgallery.core.data.mapper

import com.marina.surfgallery.core.domain.entity.Picture
import com.marina.surfgallery.core.data.remote.entity.PictureDto

fun List<PictureDto>.toDomain(): List<Picture> {
    return map {
        Picture(
            id = it.id,
            title = it.title,
            content = it.content,
            photoUrl = it.photoUrl,
            publicationDate = it.publicationDate
        )
    }
}