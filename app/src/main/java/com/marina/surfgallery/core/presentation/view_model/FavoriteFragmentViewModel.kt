package com.marina.surfgallery.core.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marina.surfgallery.common.Resource
import com.marina.surfgallery.core.domain.use_case.DeletePictureUseCase
import com.marina.surfgallery.core.domain.use_case.GetFavoritePicturesUseCase
import com.marina.surfgallery.core.presentation.entity.PictureItem
import com.marina.surfgallery.core.presentation.mapper.toPresentation
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(
    private val deletePictureUseCase: DeletePictureUseCase,
    private val getFavoritePicturesUseCase: GetFavoritePicturesUseCase
) : ViewModel() {

    private var _picturesList = MutableLiveData<Resource<List<PictureItem>>>()

    val picturesList: LiveData<Resource<List<PictureItem>>> get() = _picturesList

    init {
        getPictures()
    }

    private fun getPictures() = viewModelScope.launch {
        getFavoritePicturesUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _picturesList.postValue(
                        Resource.Success(
                            result.data?.toPresentation() ?: listOf()
                        )
                    )
                }
                is Resource.Loading -> {
                    _picturesList.postValue(Resource.Loading())
                }
                is Resource.Error -> {
                    _picturesList.postValue(Resource.Error(_picturesList.value?.message.toString()))
                }
            }
        }
    }

    fun deletePicture(pictureItem: PictureItem) = viewModelScope.launch {
        deletePictureUseCase(pictureItem.id, pictureItem.photoUrl)
        getPictures()
    }
}