package com.marina.surfgallery.home.data.remote

import com.marina.surfgallery.home.data.remote.entity.PictureResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface PictureApi {
    @GET("picture")
    suspend fun getAllPictures(@Header("Authorization") token: String): PictureResponse
}