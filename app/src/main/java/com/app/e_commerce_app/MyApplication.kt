package com.app.e_commerce_app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : MultiDexApplication() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}