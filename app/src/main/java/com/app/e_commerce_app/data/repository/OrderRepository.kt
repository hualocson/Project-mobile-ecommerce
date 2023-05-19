package com.app.e_commerce_app.data.repository

import android.util.Log
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.OrderRemoteService
import com.app.e_commerce_app.model.UpdateOrderRequest
import com.app.e_commerce_app.model.order.OrderRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

    suspend fun getAllOrderByUserID(id: Int) = withContext(Dispatchers.IO) {
        when(val response = orderRemoteService.getAllOrderByUserID(id)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun createOrder(orderRequest: OrderRequest) = withContext(Dispatchers.IO + SupervisorJob()) {
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

    suspend fun getOrderById(id: Int) = withContext(Dispatchers.IO) {
        when(val response = orderRemoteService.getOrderById(id)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun upDateOrder(id: Int, updateOrderRequest: UpdateOrderRequest) = withContext(Dispatchers.IO){
        when(val response = orderRemoteService.upDateOrder(id, updateOrderRequest)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }
}