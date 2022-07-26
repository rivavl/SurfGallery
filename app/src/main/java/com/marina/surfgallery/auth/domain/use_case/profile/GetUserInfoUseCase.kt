package com.marina.surfgallery.auth.domain.use_case.profile

import android.util.Log
import com.marina.surfgallery.auth.domain.repository.AuthRepository
import com.marina.surfgallery.common.entity.Resource
import com.marina.surfgallery.common.entity.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Flow<Resource<UserInfo>> = flow {
        try {
            emit(Resource.Loading())
            val userInfo = repository.getUserInfo()
            Log.e("GetUserInfoUseCase", userInfo.toString())
            emit(Resource.Success(userInfo))
        } catch (e: IOException) {
            emit(Resource.Error("Отсутствует интернет соединение\n Попробуйте позже"))
        }
    }
}