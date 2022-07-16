package com.marina.surfgallery.auth.domain.use_case.request

import com.marina.surfgallery.auth.domain.repository.AuthRepository
import com.marina.surfgallery.core.util.Resource
import com.marina.surfgallery.core.domain.entity.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(login: String, password: String): Flow<Resource<UserInfo>> = flow {
        try {
            emit(Resource.Loading())
            val userInfo = repository.login(login, password)
            emit(Resource.Success(userInfo))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        } catch (e: IOException) {
            emit(Resource.Error("Отсутствует интернет соединение\n Попробуйте позже"))
        }
    }
}