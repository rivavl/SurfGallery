package com.marina.surfgallery.core.data.util

import com.marina.surfgallery.auth.data.remote.AuthApi
import com.marina.surfgallery.home.data.remote.PictureApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {

        private const val BASE_URL = "https://pictures.chronicker.fun/api/"

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val authApi by lazy {
            retrofit.create(AuthApi::class.java)
        }

        val pictureApi by lazy {
            retrofit.create(PictureApi::class.java)
        }
    }
}