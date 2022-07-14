package com.marina.surfgallery.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marina.surfgallery.auth.domain.use_case.request.LoginUseCase
import com.marina.surfgallery.auth.domain.use_case.validation.ValidateLoginUseCase
import com.marina.surfgallery.auth.domain.use_case.validation.ValidatePasswordUseCase
import com.marina.surfgallery.auth.domain.util.Resource
import com.marina.surfgallery.auth.presentation.entity.FieldsState
import kotlinx.coroutines.launch

class LoginFragmentViewModel(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableLiveData<FieldsState>()
    val loginState: LiveData<FieldsState> get() = _loginState

    private val _passwordState = MutableLiveData<FieldsState>()
    val passwordState: LiveData<FieldsState> get() = _passwordState

    private var isValid: Boolean = false

    private val _isValidToken = MutableLiveData<Boolean>()
    val isValidToken: LiveData<Boolean> get() = _isValidToken


    fun checkLoginAndPassword(login: String, password: String) = viewModelScope.launch {
        checkLogin(login)
        checkPassword(password)
        loginUseCase(login, password).collect {
            when (it) {
                is Resource.Success -> {
                    _isValidToken.postValue(true)
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    private fun checkLogin(login: String) {
        val resultLogin = validateLoginUseCase(login)
        when (resultLogin.successful) {
            true -> {
                _loginState.postValue(FieldsState.Success(login = login))
                isValid = true
            }
            false -> {
                _loginState.postValue(FieldsState.LoginError(message = resultLogin.errorMessage.toString()))
                isValid = false
            }
        }
    }

    private fun checkPassword(password: String) {
        val resultPassword = validatePasswordUseCase(password)
        when (resultPassword.successful) {
            true -> {
                if (isValid) {
                    _passwordState.postValue(FieldsState.Success(password = password))
                }
            }
            false -> {
                _passwordState.postValue(FieldsState.PasswordError(message = resultPassword.errorMessage.toString()))
                isValid = false
            }
        }
    }
}