package com.marina.surfgallery.profile.data.repository

import com.marina.surfgallery.common.DataSourceHelper
import com.marina.surfgallery.common.UserInfo
import com.marina.surfgallery.profile.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val dataSourceHelper: DataSourceHelper
) : ProfileRepository {

    override suspend fun getUserInfo(): UserInfo {
        return dataSourceHelper.getUserInfo()
    }
}