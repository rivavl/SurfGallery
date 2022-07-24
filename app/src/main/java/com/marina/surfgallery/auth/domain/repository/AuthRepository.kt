package com.marina.surfgallery.auth.domain.repository

import com.marina.surfgallery.common.Result

interface AuthRepository {
    suspend fun login(login: String, password: String): Result
}