package com.marina.surfgallery.core.domain.repository

import com.marina.surfgallery.core.domain.entity.Picture

interface PictureRepository {
    suspend fun getPictures(): List<Picture>
    suspend fun savePicture(picture: Picture)
}