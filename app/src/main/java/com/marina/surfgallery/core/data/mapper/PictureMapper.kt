package com.marina.surfgallery.core.data.mapper

import com.marina.surfgallery.core.data.local.db.entity.PictureDB
import com.marina.surfgallery.core.data.remote.entity.PictureDto
import com.marina.surfgallery.core.domain.entity.Picture

fun List<PictureDto>.toDomain(): List<Picture> {
    return map {
        Picture(
            id = it.id,
            title = it.title,
            content = it.content,
            photoUrl = it.photoUrl,
            publicationDate = it.publicationDate,
            isFavorite = false
        )
    }
}

fun Picture.toPictureDB(): PictureDB {
    return PictureDB(
        id = id,
        title = title,
        content = content,
        photoUrl = photoUrl,
        publicationDate = publicationDate
    )
}