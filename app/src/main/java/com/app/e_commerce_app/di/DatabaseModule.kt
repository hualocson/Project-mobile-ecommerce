package com.app.e_commerce_app.di

import android.content.Context
import androidx.room.Room
import com.app.e_commerce_app.database.CartDatabase
import com.app.e_commerce_app.database.dao.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): CartDatabase{
        return Room.databaseBuilder(context,CartDatabase::class.java,"app_db").build()
    }

    @Provides
    fun provideCustomerDao(appDatabase: CartDatabase): CartDao{
        return appDatabase.getCartDao()
    }

}