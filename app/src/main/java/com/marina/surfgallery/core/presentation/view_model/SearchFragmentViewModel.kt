package com.marina.surfgallery.core.presentation.view_model

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marina.surfgallery.common.Resource
import com.marina.surfgallery.core.domain.use_case.DeletePictureUseCase
import com.marina.surfgallery.core.domain.use_case.GetFilteredPicturesUseCase
import com.marina.surfgallery.core.domain.use_case.SaveBitmapUseCase
import com.marina.surfgallery.core.domain.use_case.SavePictureInfoUseCase
import com.marina.surfgallery.core.presentation.entity.PictureItem
import com.marina.surfgallery.core.presentation.mapper.toPicture
import com.marina.surfgallery.core.presentation.mapper.toPresentation
import com.marina.surfgallery.core.presentation.util.SearchResult
import kotlinx.coroutines.launch

class SearchFragmentViewModel(
    private val getFilteredPicturesUseCase: GetFilteredPicturesUseCase,
    private val saveBitmapUseCase: SaveBitmapUseCase,
    private val savePictureInfoUseCase: SavePictureInfoUseCase,
    private val deletePictureUseCase: DeletePictureUseCase,
) : ViewModel() {

    private var _picturesList = MutableLiveData<SearchResult<List<PictureItem>>?>()
    val picturesList: MutableLiveData<SearchResult<List<PictureItem>>?> get() = _picturesList

    fun getPictures(query: String) = viewModelScope.launch {
        Log.e("setOnQueryTextListener getPictures", query)
        getFilteredPicturesUseCase(query.lowercase()).collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data?.isNotEmpty() == true) {
                        _picturesList.postValue(SearchResult.Success(result.data.toPresentation()))
                    } else {
                        _picturesList.postValue(SearchResult.NoResults())
                    }

                }
                is Resource.Loading -> {
                    _picturesList.postValue(SearchResult.Loading())
                }
                is Resource.Error -> {
                    _picturesList.postValue(SearchResult.Error(result.message.toString()))
                }
            }
        }
    }

    fun clearPics() {
        _picturesList.value = SearchResult.IsEmpty()
        Log.e("setOnQueryTextListener clearPics", _picturesList.value?.data.toString())
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