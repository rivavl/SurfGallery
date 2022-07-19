package com.marina.surfgallery.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marina.surfgallery.core.domain.use_case.DeletePictureUseCase
import com.marina.surfgallery.core.domain.use_case.GetFilteredPicturesUseCase
import com.marina.surfgallery.core.domain.use_case.SaveBitmapUseCase
import com.marina.surfgallery.core.domain.use_case.SavePictureInfoUseCase
import com.marina.surfgallery.core.presentation.view_model.SearchFragmentViewModel

class SearchViewModelFactory(
    private val getFilteredPicturesUseCase: GetFilteredPicturesUseCase,
    private val saveBitmapUseCase: SaveBitmapUseCase,
    private val savePictureInfoUseCase: SavePictureInfoUseCase,
    private val deletePictureUseCase: DeletePictureUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchFragmentViewModel::class.java)) {
            return SearchFragmentViewModel(
                getFilteredPicturesUseCase,
                saveBitmapUseCase,
                savePictureInfoUseCase,
                deletePictureUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}