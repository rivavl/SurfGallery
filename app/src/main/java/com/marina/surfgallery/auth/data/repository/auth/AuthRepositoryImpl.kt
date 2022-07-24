package com.marina.surfgallery.auth.data.repository.auth

import com.marina.surfgallery.auth.data.local.AuthDao
import com.marina.surfgallery.auth.data.mappers.toUserInfo
import com.marina.surfgallery.auth.data.remote.AuthApi
import com.marina.surfgallery.auth.data.remote.entity.request.LoginRequestBody
import com.marina.surfgallery.auth.domain.repository.AuthRepository
import com.marina.surfgallery.common.*
import com.marina.surfgallery.common.entity.Result
import com.marina.surfgallery.common.entity.UserInfo
import com.marina.surfgallery.core.data.local.file.SavePictureInStorage
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val dataSourceHelper: DataSourceHelper,
    private val authDao: AuthDao,
    private val saveHelper: SavePictureInStorage
) : AuthRepository {

    override suspend fun login(login: String, password: String): Result {
        val body = LoginRequestBody(phone = login, password = password)
        val response = api.login(body)
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

    override suspend fun getUserInfo(): UserInfo {
        return dataSourceHelper.getUserInfo()
    }

    override suspend fun logout() {
        dataSourceHelper.deleteUserInfo()
        authDao.dropTable()
        saveHelper.deleteAllPictures()
    }
}