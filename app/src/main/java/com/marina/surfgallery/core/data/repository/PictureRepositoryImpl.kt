package com.marina.surfgallery.core.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.marina.surfgallery.common.DataSourceHelper
import com.marina.surfgallery.common.RetrofitInstance
import com.marina.surfgallery.core.data.local.db.AppDatabase
import com.marina.surfgallery.core.data.local.file.InternalStoragePicture
import com.marina.surfgallery.core.data.local.file.SavePictureInStorage
import com.marina.surfgallery.core.data.mapper.toDomain
import com.marina.surfgallery.core.data.mapper.toPictureDB
import com.marina.surfgallery.core.domain.entity.Picture
import com.marina.surfgallery.core.domain.repository.PictureRepository

class PictureRepositoryImpl(
//    private val api:PictureApi,
//    private val dao: PictureDao,
    private val database: AppDatabase,
    private val dataSourceHelper: DataSourceHelper,
    private val saveHelper: SavePictureInStorage
) : PictureRepository {

    private val dao = database.pictureDao()

    override suspend fun getPictures(): List<Picture> {
        val token = dataSourceHelper.getUserInfo().token
        val favoritePics = dao.getIdsOfFavoritePictures()
        val allPics = RetrofitInstance.pictureApi.getAllPictures("Token $token").toList().toDomain()
        Log.d("PictureRepositoryImpl", "all: $allPics")
        Log.e("PictureRepositoryImpl", "db: $favoritePics")
        for (i in allPics.indices) {
            if (allPics[i].id in favoritePics) {
                allPics[i].isFavorite = true
            }
        }
        Log.d("PictureRepositoryImpl", "all after: $allPics")
        return allPics
    }

    override suspend fun savePicture(picture: Picture) {
        dao.savePicture(picture.toPictureDB())
    }

    override suspend fun saveBitmap(item: Bitmap, name: String) {
        val urlToArray = name.split("/")
        val nameInDb = urlToArray[urlToArray.size - 1]
        saveHelper.savePicture(InternalStoragePicture(nameInDb, item))
    }

    override suspend fun getFavoritePictures(): List<Picture> {
        return dao.getFavoritePictures().map { it.toPicture() }
    }

    override suspend fun deletePicture(id: String, name: String) {
        Log.d("PictureRepositoryImpl", "id: $id; name: $name")
        val urlToArray = name.split("/")
        val nameInDb = urlToArray[urlToArray.size - 1]
        Log.d("PictureRepositoryImpl", "nameInDb: $nameInDb")
        saveHelper.deletePicture(nameInDb)
        dao.deletePicture(id)
    }
}