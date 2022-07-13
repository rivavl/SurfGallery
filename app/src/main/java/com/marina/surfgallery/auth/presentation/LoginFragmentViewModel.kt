package com.marina.surfgallery.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marina.surfgallery.auth.domain.use_case.ValidateLoginUseCase
import com.marina.surfgallery.auth.domain.use_case.ValidatePasswordUseCase
import com.marina.surfgallery.auth.presentation.entity.FieldsState

class LoginFragmentViewModel(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _loginState = MutableLiveData<FieldsState>()
    val loginState: LiveData<FieldsState> get() = _loginState

    private val _passwordState = MutableLiveData<FieldsState>()
    val passwordState: LiveData<FieldsState> get() = _passwordState

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> get() = _isValid

    fun checkLoginAndPassword(login: String, password: String) {
        checkLogin(login)
        checkPassword(password)
    }

    private fun checkLogin(login: String) {
        val resultLogin = validateLoginUseCase(login)
        when (resultLogin.successful) {
            true -> {
                _loginState.postValue(FieldsState.Success(login = login))
                _isValid.postValue(true)
            }
            false -> {
                _loginState.postValue(FieldsState.LoginError(message = resultLogin.errorMessage.toString()))
                _isValid.postValue(false)
            }
        }
    }

    private fun checkPassword(password: String) {
        val resultPassword = validatePasswordUseCase(password)
        when (resultPassword.successful) {
            true -> {
                if (isValidFormat()) {
                    _passwordState.postValue(FieldsState.Success(password = password))
                }
            }
            false -> {
                _passwordState.postValue(FieldsState.PasswordError(message = resultPassword.errorMessage.toString()))
                _isValid.postValue(false)
            }
        }
    }

    private fun isValidFormat(): Boolean {
        return _isValid.value == true
    }

}