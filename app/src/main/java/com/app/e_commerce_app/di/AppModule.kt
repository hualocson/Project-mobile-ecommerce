package com.app.e_commerce_app.di

import android.content.Context
import com.app.e_commerce_app.MyApplication
import com.app.e_commerce_app.common.AppSharePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAppSharePreference(@ApplicationContext context: Context): AppSharePreference {
        return AppSharePreference(context)
    }

}