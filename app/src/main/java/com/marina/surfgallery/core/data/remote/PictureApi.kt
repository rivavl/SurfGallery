package com.marina.surfgallery.core.data.remote

import com.marina.surfgallery.core.data.remote.entity.PictureResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PictureApi {
    @GET("picture")
    suspend fun getAllPictures(@Header("Authorization") token: String): Response<PictureResponse>
}