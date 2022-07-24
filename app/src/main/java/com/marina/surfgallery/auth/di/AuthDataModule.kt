package com.marina.surfgallery.auth.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.marina.surfgallery.auth.data.local.AuthDao
import com.marina.surfgallery.auth.data.remote.AuthApi
import com.marina.surfgallery.auth.data.repository.auth.AuthRepositoryImpl
import com.marina.surfgallery.auth.domain.repository.AuthRepository
import com.marina.surfgallery.common.DataSourceHelper
import com.marina.surfgallery.common.SharedPrefsHelper
import com.marina.surfgallery.common.singleton.AppDatabase
import com.marina.surfgallery.common.singleton.RetrofitInstance
import com.marina.surfgallery.common.util.Constants
import com.marina.surfgallery.core.data.local.file.SavePictureInFile
import com.marina.surfgallery.core.data.local.file.SavePictureInStorage
import com.marina.surfgallery.di.AppComponent
import com.marina.surfgallery.di.ApplicationScope
import dagger.*

@Module
interface AuthDataModule {

    @ApplicationScope
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository


    @ApplicationScope
    @Binds
    fun bindSavePictureInStorage(impl: SavePictureInFile): SavePictureInStorage

    @ApplicationScope
    @Binds
    fun bindDataSourceHelper(impl: SharedPrefsHelper): DataSourceHelper

//    @Component.Builder
//    interface AppCompBuilder {
//        fun buildAppComp(): AppComponent
//
//        @BindsInstance
//        fun context(context: Context): AppCompBuilder
//    }

    companion object {
        @ApplicationScope
        @Provides
        fun provideAuthDao(database: AppDatabase): AuthDao {
            return database.authDao()
        }

        @ApplicationScope
        @Provides
        fun provideDatabase(context: Application): AppDatabase {
            return AppDatabase.getInstance(context)
        }

        @ApplicationScope
        @Provides
        fun provideAuthApi(): AuthApi {
            return RetrofitInstance.authApi
        }

        @ApplicationScope
        @Provides
        fun provideSharedPrefs(context: Application): SharedPreferences {
            return context.getSharedPreferences(Constants.SHARED_PREFS_NAME, 0)
        }
    }
}