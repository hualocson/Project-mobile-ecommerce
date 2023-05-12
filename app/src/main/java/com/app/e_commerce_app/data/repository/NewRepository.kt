package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.NewRemoteService
import com.app.e_commerce_app.data.services.ShippingRemoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewRepository @Inject constructor(private val newRemoteService: NewRemoteService) {
    suspend fun getAllNews()  = withContext(Dispatchers.IO) {
        when(val response = newRemoteService.getAllNews()) {
            is NetWorkResult.Success -> {
                response.data.data
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }
}