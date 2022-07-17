package com.marina.surfgallery.core.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("reason")
    val reason: String
)