package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.ProductApi
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.model.product.ProductModel
import javax.inject.Inject

class ProductRemoteService @Inject constructor(private val productApi: ProductApi) :
    BaseRemoteService() {
    suspend fun getAllProducts(): NetWorkResult<CustomResponse<List<ProductModel>>> =
        handleApi { productApi.getAllProducts() }

    suspend fun getProductsByCategory(id: Int): NetWorkResult<CustomResponse<List<ProductModel>>> =
        handleApi { productApi.getProductsByCategory(id) }

    suspend fun getProductsById(id: Int): NetWorkResult<CustomResponse<ProductModel>> =
        handleApi { productApi.getProductsById(id) }

    suspend fun getProductsItems(id: Int): NetWorkResult<CustomResponse<ProductItemJson>> =
        handleApi { productApi.getProductsItems(id) }
}