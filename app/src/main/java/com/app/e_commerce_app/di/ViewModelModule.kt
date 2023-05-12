package com.app.e_commerce_app.di

import com.app.e_commerce_app.data.repository.ShippingRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.viewmodel.CheckoutViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    @Singleton
    fun provideCheckoutViewModel(
        userRepository: UserRepository,
        shippingRepository: ShippingRepository
    ): CheckoutViewModel {
        return CheckoutViewModel(userRepository, shippingRepository)
    }
}