package com.marina.surfgallery.core.presentation.view_model

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marina.surfgallery.common.entity.Resource
import com.marina.surfgallery.core.domain.use_case.DeletePictureUseCase
import com.marina.surfgallery.core.domain.use_case.GetFavoritePicturesUseCase
import com.marina.surfgallery.core.presentation.entity.PictureItem
import com.marina.surfgallery.core.presentation.mapper.toPresentation
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

class FavoriteFragmentViewModel @Inject constructor(
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
                            result.data?.toPresentation()?.map {
                                it.copy(
                                    publicationDate = formatDate(
                                        it.publicationDate
                                    )
                                )
                            } ?: listOf()
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

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(date: String): String {
        val formatter = SimpleDateFormat("dd.MM.yy")
        val date = formatter.format(date.toLong())
        return date.toString()
    }

    fun deletePicture(pictureItem: PictureItem) = viewModelScope.launch {
        deletePictureUseCase(pictureItem.id, pictureItem.photoUrl)
        getPictures()
    }
}