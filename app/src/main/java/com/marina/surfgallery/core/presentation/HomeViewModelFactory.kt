package com.marina.surfgallery.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marina.surfgallery.core.domain.use_case.GetAllPicturesUseCase

class HomeViewModelFactory(
    private val getAllPicturesUseCase: GetAllPicturesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            return HomeFragmentViewModel(
                getAllPicturesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}