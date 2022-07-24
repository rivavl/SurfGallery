package com.marina.surfgallery.auth.domain.use_case.request

import com.marina.surfgallery.auth.domain.repository.AuthRepository
import com.marina.surfgallery.common.Resource
import com.marina.surfgallery.common.Result.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class LoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(login: String, password: String): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val userInfo = repository.login(login, password)
            when(userInfo) {
                SUCCESS -> emit(Resource.Success(Unit))
                ERROR -> emit(Resource.Error("Логин или пароль введен неправильно"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Отсутствует интернет соединение\n Попробуйте позже"))
        }
    }
}