package com.marina.surfgallery.core.data.local.file

interface SavePictureInStorage {
    suspend fun savePicture(picture: InternalStoragePicture)

    suspend fun deletePicture(name: String)

    suspend fun loadPicture(name: String): InternalStoragePicture

    suspend fun loadAllPictures(): List<InternalStoragePicture>
}