package com.marina.surfgallery.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marina.surfgallery.profile.domain.use_case.GetUserInfoUseCase
import com.marina.surfgallery.profile.presentation.ProfileViewModel

class ProfileViewModelFactory(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(
                getUserInfoUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
