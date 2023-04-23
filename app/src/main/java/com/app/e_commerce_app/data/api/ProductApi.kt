package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CategoryData
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.ProductData
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET(Constants.PRODUCT_URL)
    suspend fun getAllProducts(): Response<CustomResponse<ProductData>>
}