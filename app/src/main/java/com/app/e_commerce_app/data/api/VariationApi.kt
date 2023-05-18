package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CategoryData
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.variation.VariationModel
import com.app.e_commerce_app.model.variation.VaritionData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VariationApi {
    @GET(ConstantsURL.VARIATION_IN_CATEGORY_URL)
    suspend fun getVariationsInCategory(@Path("id") id : Int): Response<CustomResponse<List<VariationModel>>>

    @GET(ConstantsURL.VARITION_ALL)
    suspend fun getAllVaritions(): Response<CustomResponse<VaritionData>>
}