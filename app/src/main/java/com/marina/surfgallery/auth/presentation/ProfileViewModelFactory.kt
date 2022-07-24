package com.marina.surfgallery.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marina.surfgallery.auth.domain.use_case.profile.GetUserInfoUseCase
import com.marina.surfgallery.auth.domain.use_case.profile.LogoutUseCase
import com.marina.surfgallery.auth.presentation.view_model.ProfileViewModel

class ProfileViewModelFactory(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(
                getUserInfoUseCase,
                logoutUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
