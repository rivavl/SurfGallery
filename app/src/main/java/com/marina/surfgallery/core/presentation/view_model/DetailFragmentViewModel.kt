package com.marina.surfgallery.core.presentation.view_model

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marina.surfgallery.core.presentation.entity.PictureItem
import java.text.SimpleDateFormat
import javax.inject.Inject

class DetailFragmentViewModel @Inject constructor() : ViewModel() {

    private var _picture = MutableLiveData<PictureItem>()

    val picture: LiveData<PictureItem> get() = _picture

    @SuppressLint("SimpleDateFormat")
    fun setPicture(pictureItem: PictureItem) {
        if (pictureItem.publicationDate.length !=  8) {
            val formatter = SimpleDateFormat("dd.MM.yy")
            Log.e("setPicture", pictureItem.toString())
            val date = formatter.format(pictureItem.publicationDate.toLong())
            _picture.postValue(pictureItem.copy(publicationDate = date))
        } else{
            _picture.postValue(pictureItem)
        }

    }
}