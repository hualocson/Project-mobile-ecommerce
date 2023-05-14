package com.app.e_commerce_app.di

import com.app.e_commerce_app.MyApplication
import com.app.e_commerce_app.common.AppSharePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideAppSharePreference(): AppSharePreference {
        return AppSharePreference(MyApplication.appContext)
    }
}