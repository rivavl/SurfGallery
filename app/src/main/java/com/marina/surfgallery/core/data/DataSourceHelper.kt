package com.marina.surfgallery.core.data

import com.marina.surfgallery.core.domain.entity.UserInfo

interface DataSourceHelper {

    fun saveUserInfo(userInfo: UserInfo)

    fun getUserInfo(): UserInfo?
}