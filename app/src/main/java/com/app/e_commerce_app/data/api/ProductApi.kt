package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.*
import com.app.e_commerce_app.model.product.ProductModel
import com.app.e_commerce_app.model.product.ProductRequest
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {
    @GET(ConstantsURL.PRODUCT_URL)
    suspend fun getAllProducts(): Response<CustomResponse<List<ProductModel>>>

    @GET(ConstantsURL.PRODUCT_BY_CATEGORY_URL)
    suspend fun getProductsByCategory(@Path("id") id: Int): Response<CustomResponse<List<ProductModel>>>

    @GET(ConstantsURL.PRODUCT_BY_ID)
    suspend fun getProductsById(@Path("productId") id: Int): Response<CustomResponse<ProductModel>>

    @PATCH(ConstantsURL.PRODUCT_BY_ID)
    suspend fun updateProduct(
        @Path("productId") id: Int,
        @Body productRequest: ProductRequest
    ): Response<CustomResponse<ResponseWithMessage>>

    @POST(ConstantsURL.PRODUCT_URL)
    suspend fun addProduct(@Body productRequest: ProductRequest): Response<CustomResponse<ResponseWithMessage>>
}