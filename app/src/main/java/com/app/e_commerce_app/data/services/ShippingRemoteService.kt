package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.ShippingApi
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.ShippingJson
import javax.inject.Inject

class ShippingRemoteService @Inject constructor(private val shippingApi: ShippingApi) :
    BaseRemoteService() {

    suspend fun getAllShippingMethod(): NetWorkResult<CustomResponse<List<ShippingJson>>> =
        handleApi { shippingApi.getAllShippingMethod() }

}