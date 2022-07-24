package com.marina.surfgallery.auth.data.repository

import com.marina.surfgallery.auth.data.mappers.toUserInfo
import com.marina.surfgallery.auth.data.remote.entity.request.LoginRequestBody
import com.marina.surfgallery.auth.domain.repository.AuthRepository
import com.marina.surfgallery.common.DataSourceHelper
import com.marina.surfgallery.common.Result
import com.marina.surfgallery.common.RetrofitInstance
import com.marina.surfgallery.common.UserInfo

class AuthRepositoryImpl(
//    private val api: AuthApi
    private val dataSourceHelper: DataSourceHelper
) : AuthRepository {

    override suspend fun login(login: String, password: String): Result {
        val body = LoginRequestBody(phone = login, password = password)
        val response = RetrofitInstance.authApi.login(body)
        val result: Result
        if (response.isSuccessful) {
            val loginResponse = response.body()!!
            val userInfo = loginResponse.userDto.toUserInfo(loginResponse.token)
            saveUserInfoIntoPrefs(userInfo)
            result = Result.SUCCESS
        } else {
            result = Result.ERROR
        }
        return result
    }

    private fun saveUserInfoIntoPrefs(userInfo: UserInfo) {
        dataSourceHelper.saveUserInfo(userInfo)
    }

}