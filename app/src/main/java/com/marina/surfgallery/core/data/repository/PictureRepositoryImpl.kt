package com.marina.surfgallery.core.data.repository

import com.marina.surfgallery.common.DataSourceHelper
import com.marina.surfgallery.common.RetrofitInstance
import com.marina.surfgallery.core.domain.entity.Picture
import com.marina.surfgallery.core.data.mapper.toDomain
import com.marina.surfgallery.core.domain.repository.PictureRepository

class PictureRepositoryImpl(
//    private val api:PictureApi,
    private val dataSourceHelper: DataSourceHelper
) : PictureRepository {
    override suspend fun getPictures(): List<Picture> {
        val token = dataSourceHelper.getUserInfo().token
        println("1111111111111111111111111111111")
        println("token $token")
        return RetrofitInstance.pictureApi.getAllPictures("Token $token").toList().toDomain()
    }

    override suspend fun savePicture(picture: Picture) {
        TODO("Not yet implemented")
    }
}