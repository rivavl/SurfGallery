package com.marina.surfgallery.common

import android.content.SharedPreferences
import android.util.Log


class SharedPrefsHelper(
    private val sharedPreferences: SharedPreferences
) : DataSourceHelper {

    override fun saveUserInfo(userInfo: UserInfo) {
        sharedPreferences.edit().apply {
            putString(USER_ID, userInfo.id)
            putString(USER_FIRST_NAME, userInfo.firstName)
            putString(USER_LAST_NAME, userInfo.lastName)
            putString(USER_QUOTE, userInfo.quote)
            putString(USER_PHOTO, userInfo.photo)
            putString(USER_CITY, userInfo.city)
            putString(USER_EMAIL, userInfo.email)
            putString(USER_PHONE, userInfo.phone)
            putString(USER_TOKEN, userInfo.token)
        }.apply()
    }

    override fun getUserInfo(): UserInfo {
        Log.e("SharedPrefsHelper", sharedPreferences.all.toString())
        return UserInfo(
            id = sharedPreferences.getString(USER_ID, "").toString(),
            firstName = sharedPreferences.getString(USER_FIRST_NAME, "").toString(),
            lastName = sharedPreferences.getString(USER_LAST_NAME, "").toString(),
            quote = sharedPreferences.getString(USER_QUOTE, "").toString(),
            photo = sharedPreferences.getString(USER_PHOTO, "").toString(),
            city = sharedPreferences.getString(USER_CITY, "").toString(),
            email = sharedPreferences.getString(USER_EMAIL, "").toString(),
            phone = sharedPreferences.getString(USER_PHONE, "").toString(),
            token = sharedPreferences.getString(USER_TOKEN, null).toString()
        )
    }

    override fun deleteUserInfo() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        const val USER_ID = "user_id"
        const val USER_FIRST_NAME = "user_first_name"
        const val USER_LAST_NAME = "user_last_name"
        const val USER_QUOTE = "user_quote"
        const val USER_PHOTO = "user_photo"
        const val USER_CITY = "user_city"
        const val USER_EMAIL = "user_email"
        const val USER_PHONE = "user_phone"
        const val USER_TOKEN = "user_token"
    }
}