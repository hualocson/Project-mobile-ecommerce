package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.VariationApi
import com.app.e_commerce_app.model.CategoryData
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VaritionData
import javax.inject.Inject

class VariationRemoteService @Inject constructor(private val variationApi: VariationApi) :
    BaseRemoteService() {
    suspend fun getVariationsInCategory(id: Int): NetWorkResult<CustomResponse<List<VariationModel>>> =
        handleApi { variationApi.getVariationsInCategory(id) }

    suspend fun getAllVaritions(): NetWorkResult<CustomResponse<VaritionData>> =
        handleApi { variationApi.getAllVaritions() }
}