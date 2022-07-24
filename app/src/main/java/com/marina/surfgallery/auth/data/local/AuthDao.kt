package com.marina.surfgallery.auth.data.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AuthDao {

    @Query("delete from picture")
    suspend fun dropTable()
}