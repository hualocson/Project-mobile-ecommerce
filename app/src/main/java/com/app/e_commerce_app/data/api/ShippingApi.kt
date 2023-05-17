package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.ShippingJson
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH

interface ShippingApi {
    @GET(ConstantsURL.SHIPPING_URL)
    suspend fun getAllShippingMethod() : Response<CustomResponse<List<ShippingJson>>>
    @PATCH(ConstantsURL.SHIPPING_UPDATE_DELETE_URL)
    suspend fun updateShippingMethod() : Response<CustomResponse<ShippingJson>>
    @DELETE(ConstantsURL.SHIPPING_UPDATE_DELETE_URL)
    suspend fun deleteShippingMethod() : Response<CustomResponse<List<ShippingJson>>>
}