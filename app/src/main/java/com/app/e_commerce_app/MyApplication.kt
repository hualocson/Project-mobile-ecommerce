package com.app.e_commerce_app

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}