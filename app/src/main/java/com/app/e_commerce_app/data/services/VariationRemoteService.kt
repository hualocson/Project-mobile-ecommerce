package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.VariationApi
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.variation.VariationModel
import javax.inject.Inject

class VariationRemoteService @Inject constructor(private val variationApi: VariationApi) :
    BaseRemoteService() {
    suspend fun getVariationsInCategory(id: Int): NetWorkResult<CustomResponse<List<VariationModel>>> =
        handleApi { variationApi.getVariationsInCategory(id) }
}