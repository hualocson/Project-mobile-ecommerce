package com.app.e_commerce_app.common

import android.content.Context
import android.content.SharedPreferences

class AppSharePreference(private val context: Context) {
    companion object{
        const val APP_SHARE_KEY = "com.app.e_commerce_app"
    }

    fun getSharedPreferences(): SharedPreferences?{
        return context.getSharedPreferences(APP_SHARE_KEY,Context.MODE_PRIVATE)
    }
}