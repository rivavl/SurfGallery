package com.marina.surfgallery.core.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marina.surfgallery.core.data.local.db.entity.PictureDB

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePicture(pictureDB: PictureDB)

    @Query("select * from picture")
    suspend fun getFavoritePictures(): List<PictureDB>

    @Query("select id from picture")
    suspend fun getIdsOfFavoritePictures(): List<String>

    @Query("delete from picture where id=:id")
    suspend fun deletePicture(id: String)
}