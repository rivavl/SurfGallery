package com.marina.surfgallery.auth.di

import androidx.lifecycle.ViewModel
import com.marina.surfgallery.auth.presentation.view_model.LoginFragmentViewModel
import com.marina.surfgallery.auth.presentation.view_model.ProfileFragmentViewModel
import com.marina.surfgallery.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginFragmentViewModel::class)
    fun bindLoginFragmentViewModel(viewModel: LoginFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileFragmentViewModel::class)
    fun bindProfileFragmentViewModel(viewModel: ProfileFragmentViewModel): ViewModel
}