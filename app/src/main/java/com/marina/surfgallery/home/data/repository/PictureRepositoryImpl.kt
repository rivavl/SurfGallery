package com.marina.surfgallery.home.data.repository

import com.marina.surfgallery.core.data.DataSourceHelper
import com.marina.surfgallery.core.data.util.RetrofitInstance
import com.marina.surfgallery.home.domain.entity.Picture
import com.marina.surfgallery.home.data.mapper.toDomain
import com.marina.surfgallery.home.domain.repository.PictureRepository

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
}