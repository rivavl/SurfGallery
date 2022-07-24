package com.marina.surfgallery.di

import android.app.Application
import com.marina.surfgallery.auth.di.AuthDataModule
import com.marina.surfgallery.auth.di.AuthViewModelModule
import com.marina.surfgallery.auth.presentation.fragment.LoginFragment
import com.marina.surfgallery.auth.presentation.fragment.ProfileFragment
import com.marina.surfgallery.common.di.CommonModule
import com.marina.surfgallery.core.di.CoreDataModule
import com.marina.surfgallery.core.di.CoreViewModelModule
import com.marina.surfgallery.core.presentation.fragment.FavoriteFragment
import com.marina.surfgallery.core.presentation.fragment.HomeFragment
import com.marina.surfgallery.core.presentation.fragment.SearchFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        AuthDataModule::class,
        AuthViewModelModule::class,
        CommonModule::class,
        CoreDataModule::class,
        CoreViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}