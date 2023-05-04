package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.variation.VariationModel

class VariationRepository {
    suspend fun getVariationsInCategory(id: Int): NetWorkResult<CustomResponse<List<VariationModel>>> =
        ApiConfig.handleApi { ApiConfig.variationApi.getVariationsInCategory(id) }
}