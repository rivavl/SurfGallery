package com.marina.surfgallery.auth.domain.repository

import com.marina.surfgallery.common.Result
import com.marina.surfgallery.common.UserInfo

interface AuthRepository {
    suspend fun login(login: String, password: String): Result

    suspend fun getUserInfo(): UserInfo

    suspend fun logout()
}