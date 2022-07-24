package com.marina.surfgallery.auth.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marina.surfgallery.auth.domain.use_case.profile.GetUserInfoUseCase
import com.marina.surfgallery.auth.domain.use_case.profile.LogoutUseCase
import com.marina.surfgallery.auth.presentation.entity.User
import com.marina.surfgallery.common.Resource
import com.marina.surfgallery.profile.presentation.mapper.toUser
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _userInfo = MutableLiveData<Resource<User>?>()
    val userInfo: LiveData<Resource<User>?> get() = _userInfo

    private val _isLogout = MutableLiveData<Resource<Unit>>()
    val isLogout: LiveData<Resource<Unit>> get() = _isLogout


    init {
        getData()
    }

    private fun getData() = viewModelScope.launch {
        getUserInfoUseCase().collect { result ->
            Log.e("getData", result.data.toString())
            Log.e("getData", result.message.toString())
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

    fun logout() = viewModelScope.launch {
        logoutUseCase().collect {
            when (it) {
                is Resource.Success -> {
                    _isLogout.postValue(Resource.Success(Unit))
                    _userInfo.postValue(null)
                }
                is Resource.Error -> {
                    _isLogout.postValue(Resource.Error(it.message!!))
                }
                is Resource.Loading -> {
                    _isLogout.postValue(Resource.Loading(Unit))
                }
            }
        }
    }
}