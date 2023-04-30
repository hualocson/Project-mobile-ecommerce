package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET(Constants.PRODUCT_URL)
    suspend fun getAllProducts(): Response<CustomResponse<ProductData>>
    @GET(Constants.PRODUCT_BY_CATEGORY_URL)
    suspend fun getProductsByCategory(@Path("id") id : Int) : Response<CustomResponse<ProductData>>

    @GET(Constants.PRODUCT_BY_ID)
    suspend fun getProductsById(@Path("productId") id : Int) : Response<CustomResponse<ProductDetailData>>
}