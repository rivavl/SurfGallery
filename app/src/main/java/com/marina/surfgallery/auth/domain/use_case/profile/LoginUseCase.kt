package com.marina.surfgallery.auth.domain.use_case.profile

import com.marina.surfgallery.auth.domain.repository.AuthRepository
import com.marina.surfgallery.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class LogoutUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            repository.logout()
            emit(Resource.Success(Unit))
        } catch (e: IOException) {
            emit(Resource.Error("Отсутствует интернет соединение\n Попробуйте позже"))
        }
    }
}