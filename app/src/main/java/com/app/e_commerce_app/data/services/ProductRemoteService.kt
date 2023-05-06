package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.ProductApi
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.product.ProductData
import com.app.e_commerce_app.model.product.ProductModel
import javax.inject.Inject

class ProductRemoteService @Inject constructor(private val productApi: ProductApi) :
    BaseRemoteService() {
    suspend fun getAllProducts(): NetWorkResult<CustomResponse<ProductData>> =
        handleApi { productApi.getAllProducts() }

    suspend fun getProductsByCategory(id: Int): NetWorkResult<CustomResponse<ProductData>> =
        handleApi { productApi.getProductsByCategory(id) }

    suspend fun getProductsById(id: Int): NetWorkResult<CustomResponse<ProductModel>> =
        handleApi { productApi.getProductsById(id) }
}