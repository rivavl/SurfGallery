package com.marina.surfgallery.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marina.surfgallery.common.Resource
import com.marina.surfgallery.profile.domain.use_case.GetUserInfoUseCase
import com.marina.surfgallery.profile.presentation.entity.User
import com.marina.surfgallery.profile.presentation.mapper.toUser
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _userInfo = MutableLiveData<Resource<User>>()
    val userInfo: LiveData<Resource<User>> get() = _userInfo

    init {
        getData()
    }

    private fun getData() = viewModelScope.launch {
        getUserInfoUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    _userInfo.postValue(
                        Resource.Success(result.data?.toUser()!!)
                    )
                }

                is Resource.Loading -> {
                    _userInfo.postValue(Resource.Loading())
                }

                is Resource.Error -> {
                    _userInfo.postValue(Resource.Error(_userInfo.value?.message.toString()))
                }
            }
        }
    }
}