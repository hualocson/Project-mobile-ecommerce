package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.variation.VariationModel

class VariationRemoteService:BaseRemoteService() {
    suspend fun getVariationsInCategory(id: Int): NetWorkResult<CustomResponse<List<VariationModel>>> =
        ApiConfig.handleApi { ApiConfig.variationApi.getVariationsInCategory(id) }
}