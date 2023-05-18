package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.*
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.model.product.ProductItemRequest
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

    @GET(ConstantsURL.PRODUCT_ITEMS)
    suspend fun getProductsItems(@Path("productItemId") id: Int): Response<CustomResponse<ProductItemJson>>

    @POST(ConstantsURL.CREATE_PRODUCT_ITEM)
    suspend fun addProductItem(
        @Path("productId") productId: Int,
        @Body createProductItemRequest: ProductItemRequest
    ): Response<CustomResponse<ProductItemJson>>

    @PATCH(ConstantsURL.PRODUCT_ITEMS)
    suspend fun updateProductItem(
        @Path("productItemId") id: Int,
        @Body createProductItemRequest: ProductItemRequest
    ): Response<CustomResponse<ProductItemJson>>
}