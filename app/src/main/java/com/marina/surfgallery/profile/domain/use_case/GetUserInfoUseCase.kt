package com.marina.surfgallery.profile.domain.use_case

import com.marina.surfgallery.auth.domain.util.Resource
import com.marina.surfgallery.core.domain.entity.UserInfo
import com.marina.surfgallery.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetUserInfoUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Flow<Resource<UserInfo>> = flow {
        try {
            emit(Resource.Loading())
            val pictures = repository.getUserInfo()
            emit(Resource.Success(pictures))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        } catch (e: IOException) {
            emit(Resource.Error("Отсутствует интернет соединение\n Попробуйте позже"))
        }
    }
}