package com.marina.surfgallery.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marina.surfgallery.auth.domain.use_case.ValidateLoginUseCase
import com.marina.surfgallery.auth.domain.use_case.ValidatePasswordUseCase

class ViewModelFactory(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)) {
            return LoginFragmentViewModel(
                validateLoginUseCase,
                validatePasswordUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}