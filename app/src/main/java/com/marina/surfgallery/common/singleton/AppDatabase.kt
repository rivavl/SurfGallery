package com.marina.surfgallery.common.singleton

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marina.surfgallery.auth.data.local.AuthDao
import com.marina.surfgallery.core.data.local.db.PictureDao
import com.marina.surfgallery.core.data.local.db.entity.PictureDB

@Database(entities = [PictureDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pictureDao(): PictureDao

    abstract fun authDao(): AuthDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "pictures.db"

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}