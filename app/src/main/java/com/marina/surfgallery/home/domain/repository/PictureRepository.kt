package com.marina.surfgallery.home.domain.repository

import com.marina.surfgallery.home.domain.entity.Picture

interface PictureRepository {
    suspend fun getPictures(): List<Picture>
}