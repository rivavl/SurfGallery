package com.marina.surfgallery.core.domain.repository

import android.graphics.Bitmap
import com.marina.surfgallery.common.Resource
import com.marina.surfgallery.core.domain.entity.Picture

interface PictureRepository {

    suspend fun getPictures(): Resource<List<Picture>>

    suspend fun savePicture(picture: Picture)

    suspend fun saveBitmap(item: Bitmap, name: String)

    suspend fun getFavoritePictures(): List<Picture>

    suspend fun deletePicture(id: String, name: String)

    suspend fun getFilteredPictures(query: String): Resource<List<Picture>>
}