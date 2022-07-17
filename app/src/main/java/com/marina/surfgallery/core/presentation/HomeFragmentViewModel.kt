package com.marina.surfgallery.core.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marina.surfgallery.common.Resource
import com.marina.surfgallery.core.domain.use_case.GetAllPicturesUseCase
import com.marina.surfgallery.core.presentation.entity.PictureItem
import com.marina.surfgallery.core.presentation.mapper.toPresentation
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val getAllPicturesUseCase: GetAllPicturesUseCase
) : ViewModel() {

    private var _picturesList = MutableLiveData<Resource<List<PictureItem>>>()

    val picturesList: LiveData<Resource<List<PictureItem>>> get() = _picturesList

    init {
        getPictures()
    }

    private fun getPictures() = viewModelScope.launch {
        getAllPicturesUseCase().collect { result ->
            println("000000000000000000000000000000000000")
            println(result.data)
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
}