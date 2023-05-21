package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.OrderApi
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.UpdateOrderRequest
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.model.order.OrderRequest
import com.app.e_commerce_app.model.order.OrderUpdateJson
import javax.inject.Inject

class OrderRemoteService @Inject constructor(private val orderApi: OrderApi) : BaseRemoteService() {
    suspend fun getAllUserOrder(): NetWorkResult<CustomResponse<List<OrderJson>>> =
        handleApi { orderApi.getAllUserOrders() }

    suspend fun getAllOrderByUserID(id: Int): NetWorkResult<CustomResponse<List<OrderJson>>> =
        handleApi { orderApi.getAllOrderByUserID(id) }

    suspend fun createOrder(orderRequest: OrderRequest): NetWorkResult<CustomResponse<OrderJson>> =
        handleApi { orderApi.createOrder(orderRequest) }

    suspend fun getOrderById(id: Int) : NetWorkResult<CustomResponse<OrderJson>> =
        handleApi { orderApi.getOrderById(id) }

    suspend fun upDateOrder(id: Int, updateOrderRequest: UpdateOrderRequest) : NetWorkResult<CustomResponse<OrderUpdateJson>> =
        handleApi { orderApi.upDateOrder(id, updateOrderRequest) }

}