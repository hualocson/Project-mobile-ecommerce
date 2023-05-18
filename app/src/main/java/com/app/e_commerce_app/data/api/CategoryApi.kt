package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.CategoryData
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRequest
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.model.product.ProductItemRequest
import retrofit2.Response
import retrofit2.http.*

interface CategoryApi {
    @POST(ConstantsURL.CATEGORY_URL)
    suspend fun addCategory(@Body categoryRequest: CategoryRequest): Response<CustomResponse<CategoryModel>>

    @GET(ConstantsURL.CATEGORY_URL)
    suspend fun getAllCategories(): Response<CustomResponse<CategoryData>>

    @GET(ConstantsURL.CATEGORY_URL_ID)
    suspend fun getCategoryById(@Path("id") id: Int): Response<CustomResponse<CategoryModel>>


    @PATCH(ConstantsURL.CATEGORY_URL_ID)
    suspend fun updateCategory(
        @Path("id") id: Int,
        @Body categoryRequest: CategoryRequest
    ): Response<CustomResponse<CategoryModel>>
}