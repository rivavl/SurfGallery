package com.marina.surfgallery.auth.domain.repository

import com.marina.surfgallery.common.entity.Result
import com.marina.surfgallery.common.entity.UserInfo

interface AuthRepository {
    suspend fun login(login: String, password: String): Result

    suspend fun getUserInfo(): UserInfo

    suspend fun logout()
}