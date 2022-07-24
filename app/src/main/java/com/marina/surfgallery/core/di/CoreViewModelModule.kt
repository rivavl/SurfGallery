package com.marina.surfgallery.core.di

import androidx.lifecycle.ViewModel
import com.marina.surfgallery.core.presentation.view_model.DetailFragmentViewModel
import com.marina.surfgallery.core.presentation.view_model.FavoriteFragmentViewModel
import com.marina.surfgallery.core.presentation.view_model.HomeFragmentViewModel
import com.marina.surfgallery.core.presentation.view_model.SearchFragmentViewModel
import com.marina.surfgallery.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CoreViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailFragmentViewModel::class)
    fun bindDetailFragmentViewModel(viewModel: DetailFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteFragmentViewModel::class)
    fun bindFavoriteFragmentViewModel(viewModel: FavoriteFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel::class)
    fun bindHomeFragmentViewModel(viewModel: HomeFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchFragmentViewModel::class)
    fun bindSearchFragmentViewModel(viewModel: SearchFragmentViewModel): ViewModel
}