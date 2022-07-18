package com.marina.surfgallery.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marina.surfgallery.core.domain.use_case.DeletePictureUseCase
import com.marina.surfgallery.core.domain.use_case.GetAllPicturesUseCase
import com.marina.surfgallery.core.domain.use_case.SaveBitmapUseCase
import com.marina.surfgallery.core.domain.use_case.SavePictureInfoUseCase
import com.marina.surfgallery.core.presentation.view_model.HomeFragmentViewModel

class HomeViewModelFactory(
    private val getAllPicturesUseCase: GetAllPicturesUseCase,
    private val deletePictureUseCase: DeletePictureUseCase,
    private val saveBitmapUseCase: SaveBitmapUseCase,
    private val savePictureInfoUseCase: SavePictureInfoUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            return HomeFragmentViewModel(
                getAllPicturesUseCase,
                deletePictureUseCase,
                saveBitmapUseCase,
                savePictureInfoUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}