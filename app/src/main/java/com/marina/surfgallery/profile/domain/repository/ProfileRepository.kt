package com.marina.surfgallery.profile.domain.repository

import com.marina.surfgallery.common.UserInfo

interface ProfileRepository {
    suspend fun getUserInfo(): UserInfo
}