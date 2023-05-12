package com.app.e_commerce_app.data.repository

import android.util.Log
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.OrderRemoteService
import com.app.e_commerce_app.model.order.OrderRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderRemoteService: OrderRemoteService) {
    suspend fun getAllUserOrder() = withContext(Dispatchers.IO) {
        when(val response = orderRemoteService.getAllUserOrder()) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun createOrder(orderRequest: OrderRequest) = withContext(Dispatchers.IO) {
        when(val response = orderRemoteService.createOrder(orderRequest)) {
            is NetWorkResult.Success -> {
                Log.d("INORDER REPOS", response.data.toString())
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                Log.d("Err", response.exception.message!!)
                throw response.exception
            }
        }
    }
}