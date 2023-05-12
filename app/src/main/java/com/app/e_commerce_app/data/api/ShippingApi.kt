package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.ShippingJson
import retrofit2.Response
import retrofit2.http.GET

interface ShippingApi {
    @GET(ConstantsURL.SHIPPING_URL)
    suspend fun getAllShippingMethod() : Response<CustomResponse<List<ShippingJson>>>
}