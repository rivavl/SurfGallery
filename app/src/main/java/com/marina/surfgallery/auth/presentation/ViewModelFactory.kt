package com.marina.surfgallery.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marina.surfgallery.auth.domain.use_case.request.LoginUseCase
import com.marina.surfgallery.auth.domain.use_case.validation.ValidateLoginUseCase
import com.marina.surfgallery.auth.domain.use_case.validation.ValidatePasswordUseCase
import com.marina.surfgallery.auth.presentation.view_model.LoginFragmentViewModel

class ViewModelFactory(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)) {
            return LoginFragmentViewModel(
                validateLoginUseCase,
                validatePasswordUseCase,
                loginUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}