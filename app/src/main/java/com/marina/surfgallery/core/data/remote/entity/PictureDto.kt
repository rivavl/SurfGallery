package com.marina.surfgallery.core.data.remote.entity

import com.google.gson.annotations.SerializedName

data class PictureDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("photoUrl")
    val photoUrl: String,
    @SerializedName("publicationDate")
    val publicationDate: Long
)