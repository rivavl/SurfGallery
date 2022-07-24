package com.marina.surfgallery.core.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marina.surfgallery.core.presentation.entity.PictureItem
import javax.inject.Inject

class DetailFragmentViewModel @Inject constructor() : ViewModel() {

    private var _picture = MutableLiveData<PictureItem>()

    val picture: LiveData<PictureItem> get() = _picture

    fun setPicture(pictureItem: PictureItem) {
        _picture.postValue(pictureItem)
    }
}