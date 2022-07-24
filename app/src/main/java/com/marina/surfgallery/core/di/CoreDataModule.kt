package com.marina.surfgallery.core.di

import com.marina.surfgallery.auth.data.repository.auth.AuthRepositoryImpl
import com.marina.surfgallery.auth.domain.repository.AuthRepository
import com.marina.surfgallery.common.singleton.AppDatabase
import com.marina.surfgallery.common.singleton.RetrofitInstance
import com.marina.surfgallery.core.data.local.db.PictureDao
import com.marina.surfgallery.core.data.remote.PictureApi
import com.marina.surfgallery.core.data.repository.PictureRepositoryImpl
import com.marina.surfgallery.core.domain.repository.PictureRepository
import com.marina.surfgallery.di.ApplicationScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface CoreDataModule {

    @ApplicationScope
    @Binds
    fun bindPictureRepository(impl: PictureRepositoryImpl): PictureRepository

    companion object {
        @Provides
        fun providePictureDao(database: AppDatabase): PictureDao {
            return database.pictureDao()
        }

        @Provides
        fun providePictureApi(): PictureApi {
            return RetrofitInstance.pictureApi
        }
    }
}