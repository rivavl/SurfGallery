package com.marina.surfgallery.common

import com.marina.surfgallery.common.entity.UserInfo

interface DataSourceHelper {

    fun saveUserInfo(userInfo: UserInfo)

    fun getUserInfo(): UserInfo

    fun deleteUserInfo()
}