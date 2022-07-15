package com.marina.surfgallery.home.data.mapper

import com.marina.surfgallery.home.domain.entity.Picture
import com.marina.surfgallery.home.data.remote.entity.PictureDto

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