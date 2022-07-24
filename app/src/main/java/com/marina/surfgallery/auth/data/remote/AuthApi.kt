package com.marina.surfgallery.auth.data.remote

import com.marina.surfgallery.auth.data.remote.entity.request.LoginRequestBody
import com.marina.surfgallery.auth.data.remote.entity.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequestBody): Response<LoginResponse>
}