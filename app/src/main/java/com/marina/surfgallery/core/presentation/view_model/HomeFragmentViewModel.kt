package com.marina.surfgallery.core.presentation.view_model

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marina.surfgallery.common.entity.Resource
import com.marina.surfgallery.core.domain.use_case.DeletePictureUseCase
import com.marina.surfgallery.core.domain.use_case.GetAllPicturesUseCase
import com.marina.surfgallery.core.domain.use_case.SaveBitmapUseCase
import com.marina.surfgallery.core.domain.use_case.SavePictureInfoUseCase
import com.marina.surfgallery.core.presentation.entity.PictureItem
import com.marina.surfgallery.core.presentation.mapper.toPicture
import com.marina.surfgallery.core.presentation.mapper.toPresentation
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    private val getAllPicturesUseCase: GetAllPicturesUseCase,
    private val deletePictureUseCase: DeletePictureUseCase,
    private val saveBitmapUseCase: SaveBitmapUseCase,
    private val savePictureInfoUseCase: SavePictureInfoUseCase
) : ViewModel() {

    private var _picturesList = MutableLiveData<Resource<List<PictureItem>>>()

    val picturesList: LiveData<Resource<List<PictureItem>>> get() = _picturesList

    init {
        getPictures()
    }

    private fun getPictures() = viewModelScope.launch {
        getAllPicturesUseCase().collect { result ->
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

    fun savePicture(pictureItem: PictureItem) = viewModelScope.launch {
        savePictureInfoUseCase(pictureItem.toPicture())
    }

    fun deletePicture(pictureItem: PictureItem) = viewModelScope.launch {
        deletePictureUseCase(pictureItem.id, pictureItem.photoUrl)
    }

    fun saveBitmap(bitmap: Bitmap, name: String) = viewModelScope.launch {
        saveBitmapUseCase(bitmap, name)
    }
}