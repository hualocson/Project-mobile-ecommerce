package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.product.ProductData
import com.app.e_commerce_app.model.product.ProductModel

class ProductRemoteService : BaseRemoteService() {
    suspend fun getAllProducts(): NetWorkResult<CustomResponse<ProductData>> =
       handleApi { ApiConfig.productApi.getAllProducts() }

    suspend fun getProductsByCategory(id: Int): NetWorkResult<CustomResponse<ProductData>> =
        ApiConfig.handleApi { ApiConfig.productApi.getProductsByCategory(id) }

    suspend fun getProductsById(id: Int): NetWorkResult<CustomResponse<ProductModel>> =
        ApiConfig.handleApi { ApiConfig.productApi.getProductsById(id) }
}