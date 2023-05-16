package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.*
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.model.product.ProductModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET(ConstantsURL.PRODUCT_URL)
    suspend fun getAllProducts(): Response<CustomResponse<List<ProductModel>>>

    @GET(ConstantsURL.PRODUCT_BY_CATEGORY_URL)
    suspend fun getProductsByCategory(@Path("id") id: Int): Response<CustomResponse<List<ProductModel>>>

    @GET(ConstantsURL.PRODUCT_BY_ID)
    suspend fun getProductsById(@Path("productId") id: Int): Response<CustomResponse<ProductModel>>

    @GET(ConstantsURL.PRODUCT_ITEMS)
    suspend fun getProductsItems(@Path("productItemId") id: Int): Response<CustomResponse<ProductItemJson>>
}