package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.ProductRemoteService
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.product.ProductData
import com.app.e_commerce_app.model.product.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(val productRemoteService: ProductRemoteService) {
//    suspend fun getAllProducts(): NetWorkResult<CustomResponse<ProductData>> =
//        ApiConfig.handleApi { ApiConfig.productApi.getAllProducts() }

    suspend fun getProductsByCategory(id: Int): NetWorkResult<CustomResponse<ProductData>> =
        ApiConfig.handleApi { ApiConfig.productApi.getProductsByCategory(id) }

    suspend fun getProductsById(id: Int): NetWorkResult<CustomResponse<ProductModel>> =
        ApiConfig.handleApi { ApiConfig.productApi.getProductsById(id) }


    suspend fun getAllProducts() = withContext(Dispatchers.IO) {
        when(val result = productRemoteService.getAllProducts()) {
            is NetWorkResult.Success -> {
                result.data.data!!.products
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }
}