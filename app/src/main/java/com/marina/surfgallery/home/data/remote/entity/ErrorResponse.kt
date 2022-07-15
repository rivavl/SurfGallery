package com.marina.surfgallery.home.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("reason")
    val reason: String
)