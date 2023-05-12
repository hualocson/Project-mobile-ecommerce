package com.app.e_commerce_app.di

import androidx.room.Index
import com.app.e_commerce_app.common.AppSharePreference
import com.app.e_commerce_app.common.AuthInterceptor
import com.app.e_commerce_app.data.api.*
import com.app.e_commerce_app.data.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.PrimitiveIterator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenRepository: TokenRepository
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(httpLoggingInterceptor)
        builder.interceptors().add(AuthInterceptor(tokenRepository))
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ConstantsURL.BASE_URL)
            .build()
    }

    @Provides
    fun provideCategoryAPI(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }

    @Provides
    fun provideProductAPI(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    fun provideUserAPI(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideVariationAPI(retrofit: Retrofit) : VariationApi {
        return retrofit.create(VariationApi::class.java)
    }

    @Provides
    fun provideShippingAPI(retrofit: Retrofit) : ShippingApi {
        return retrofit.create(ShippingApi::class.java)
    }

    @Provides
    fun provideOrderAPI(retrofit: Retrofit) : OrderApi {
        return retrofit.create(OrderApi::class.java)
    }
    @Provides
    fun provideNewAPI(retrofit: Retrofit) : NewApi {
        return retrofit.create(NewApi::class.java)
    }
}