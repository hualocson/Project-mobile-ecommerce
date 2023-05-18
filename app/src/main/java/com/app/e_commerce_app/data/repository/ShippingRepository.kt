package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.ShippingRemoteService
import com.app.e_commerce_app.model.ShippingJson
import com.app.e_commerce_app.model.ShippingRequest
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

    suspend fun updateShippingMethod(id: Int, shippingRequest: ShippingRequest)= withContext(Dispatchers.IO){
    when(val response = shippingRemoteService.updateShippingMethod(id,shippingRequest)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun deleteShippingMethod(id: Int) {
        when(val response = shippingRemoteService.deleteShippingMethod(id)) {
            is NetWorkResult.Success<*> -> {
                response.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun addShippingMethod(shippingRequest: ShippingRequest): ShippingJson {
        when(val response = shippingRemoteService.addShippingMethod(shippingRequest)) {
            is NetWorkResult.Success -> {
                return response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }
}