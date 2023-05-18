package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.ShippingJson
import com.app.e_commerce_app.model.ShippingRequest
import retrofit2.Response
import retrofit2.http.*

interface ShippingApi {
    @GET(ConstantsURL.SHIPPING_URL)
    suspend fun getAllShippingMethod() : Response<CustomResponse<List<ShippingJson>>>
    @PATCH(ConstantsURL.SHIPPING_UPDATE_DELETE_URL)
    suspend fun updateShippingMethod(@Path("id") id: Int, @Body shippingRequest: ShippingRequest) : Response<CustomResponse<ShippingJson>>
    @DELETE(ConstantsURL.SHIPPING_UPDATE_DELETE_URL)
    suspend fun deleteShippingMethod(@Path("id") id: Int) : Response<CustomResponse<Nothing>>
    @POST(ConstantsURL.SHIPPING_URL)
    suspend fun addShippingMethod(@Body shippingRequest: ShippingRequest) : Response<CustomResponse<ShippingJson>>
}