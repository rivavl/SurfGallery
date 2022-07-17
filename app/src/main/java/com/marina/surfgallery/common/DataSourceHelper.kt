package com.marina.surfgallery.common

interface DataSourceHelper {

    fun saveUserInfo(userInfo: UserInfo)

    fun getUserInfo(): UserInfo
}