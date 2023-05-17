package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.ShippingRemoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShippingRepository @Inject constructor(private val shippingRemoteService: ShippingRemoteService) {
    suspend fun getAllShippingMethod()  = withContext(Dispatchers.IO) {
        when(val response = shippingRemoteService.getAllShippingMethod()) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }
}