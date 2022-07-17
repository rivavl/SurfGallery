package com.marina.surfgallery.auth.domain.repository

import com.marina.surfgallery.common.UserInfo

interface AuthRepository {
    suspend fun login(login: String, password: String): UserInfo
}