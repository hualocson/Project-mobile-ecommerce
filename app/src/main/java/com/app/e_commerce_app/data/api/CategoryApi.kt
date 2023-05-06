package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CategoryData
import com.app.e_commerce_app.model.CustomResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {
    @GET(ConstantsURL.CATEGORY_URL)
    suspend fun getAllCategories(): Response<CustomResponse<CategoryData>>
}