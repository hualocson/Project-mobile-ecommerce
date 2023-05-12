package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.model.order.OrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderApi {
    @GET(ConstantsURL.ORDER_URL)
    suspend fun getAllUserOrders(): Response<CustomResponse<List<OrderJson>>>

    @POST(ConstantsURL.ORDER_URL)
    suspend fun createOrder(@Body orderRequest: OrderRequest): Response<CustomResponse<OrderJson>>
}