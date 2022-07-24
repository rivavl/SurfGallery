package com.marina.surfgallery.core.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.marina.surfgallery.common.DataSourceHelper
import com.marina.surfgallery.common.entity.Resource
import com.marina.surfgallery.common.singleton.AppDatabase
import com.marina.surfgallery.common.singleton.RetrofitInstance
import com.marina.surfgallery.core.data.local.db.PictureDao
import com.marina.surfgallery.core.data.local.file.InternalStoragePicture
import com.marina.surfgallery.core.data.local.file.SavePictureInStorage
import com.marina.surfgallery.core.data.mapper.toDomain
import com.marina.surfgallery.core.data.mapper.toPictureDB
import com.marina.surfgallery.core.data.remote.PictureApi
import com.marina.surfgallery.core.domain.entity.Picture
import com.marina.surfgallery.core.domain.repository.PictureRepository
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val api: PictureApi,
    private val dao: PictureDao,
    private val dataSourceHelper: DataSourceHelper,
    private val saveHelper: SavePictureInStorage
) : PictureRepository {

    override suspend fun getPictures(): Resource<List<Picture>> {
        val token = dataSourceHelper.getUserInfo().token
        val favoritePics = dao.getIdsOfFavoritePictures()
        val response = api.getAllPictures("Token $token")

        val result: Resource<List<Picture>>

        if (response.isSuccessful) {
            val allPics = response.body()!!.toList().toDomain()
            for (i in allPics.indices) {
                if (allPics[i].id in favoritePics) {
                    allPics[i].isFavorite = true
                }
            }
            result = Resource.Success(allPics)
        } else {
            result = Resource.Error("Ошибка")
        }
        return result
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

    override suspend fun getFilteredPictures(query: String): Resource<List<Picture>> {
        val result: Resource<List<Picture>>
        val response = getPictures()
        if (response is Resource.Success) {
            val data = response.data?.filter { it.title.lowercase().contains(query) }
            result = data?.let { Resource.Success(it) }!!
        } else {
            result = Resource.Error(response.message!!)
        }
        return result
    }
}