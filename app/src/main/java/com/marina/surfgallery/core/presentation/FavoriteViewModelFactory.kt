package com.marina.surfgallery.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marina.surfgallery.core.domain.use_case.DeletePictureUseCase
import com.marina.surfgallery.core.domain.use_case.GetFavoritePicturesUseCase
import com.marina.surfgallery.core.presentation.view_model.FavoriteFragmentViewModel

class FavoriteViewModelFactory(
    private val deletePictureUseCase: DeletePictureUseCase,
    private val getFavoritePicturesUseCase: GetFavoritePicturesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteFragmentViewModel::class.java)) {
            return FavoriteFragmentViewModel(
                deletePictureUseCase,
                getFavoritePicturesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}