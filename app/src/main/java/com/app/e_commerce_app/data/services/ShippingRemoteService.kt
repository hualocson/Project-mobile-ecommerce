package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.ShippingApi
import com.app.e_commerce_app.model.ChooseItem
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.ShippingJson
import com.app.e_commerce_app.model.ShippingRequest
import javax.inject.Inject

class ShippingRemoteService @Inject constructor(private val shippingApi: ShippingApi) :
    BaseRemoteService() {

    suspend fun getAllShippingMethod(): NetWorkResult<CustomResponse<List<ShippingJson>>> =
        handleApi { shippingApi.getAllShippingMethod() }

    suspend fun updateShippingMethod(id: Int, shippingRequest: ShippingRequest): NetWorkResult<CustomResponse<ShippingJson>> =
        handleApi { shippingApi.updateShippingMethod(id,shippingRequest) }

    suspend fun deleteShippingMethod(id: Int): NetWorkResult<CustomResponse<Nothing>> =
        handleApi { shippingApi.deleteShippingMethod(id) }

    suspend fun addShippingMethod(shippingRequest: ShippingRequest): NetWorkResult<CustomResponse<ShippingJson>> =
         handleApi { shippingApi.addShippingMethod(shippingRequest) }

}