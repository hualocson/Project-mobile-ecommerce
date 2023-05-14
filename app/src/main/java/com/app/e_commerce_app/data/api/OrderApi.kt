package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.order.OrderJson
import com.app.e_commerce_app.model.order.OrderRequest
import com.app.e_commerce_app.model.product.ProductModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {
    @GET(ConstantsURL.ORDER_URL)
    suspend fun getAllUserOrders(): Response<CustomResponse<List<OrderJson>>>

    @POST(ConstantsURL.ORDER_URL)
    suspend fun createOrder(@Body orderRequest: OrderRequest): Response<CustomResponse<OrderJson>>

    @GET(ConstantsURL.ORDER_BY_ID)
    suspend fun getOrderById(@Path("id") id: Int): Response<CustomResponse<OrderJson>>
}