package com.marina.surfgallery.profile.domain.repository

import com.marina.surfgallery.core.domain.entity.UserInfo

interface ProfileRepository {
    suspend fun getUserInfo(): UserInfo
}