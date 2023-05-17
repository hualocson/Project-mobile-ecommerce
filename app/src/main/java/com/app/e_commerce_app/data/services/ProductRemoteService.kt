package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.ProductApi
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.ResponseWithMessage
import com.app.e_commerce_app.model.product.ProductItemJson
import com.app.e_commerce_app.model.product.ProductItemRequest
import com.app.e_commerce_app.model.product.ProductModel
import com.app.e_commerce_app.model.product.ProductRequest
import javax.inject.Inject

class ProductRemoteService @Inject constructor(private val productApi: ProductApi) :
    BaseRemoteService() {
    suspend fun getAllProducts(): NetWorkResult<CustomResponse<List<ProductModel>>> =
        handleApi { productApi.getAllProducts() }

    suspend fun getProductsByCategory(id: Int): NetWorkResult<CustomResponse<List<ProductModel>>> =
        handleApi { productApi.getProductsByCategory(id) }

    suspend fun getProductsById(id: Int): NetWorkResult<CustomResponse<ProductModel>> =
        handleApi { productApi.getProductsById(id) }

    suspend fun updateProduct(id: Int, productRequest: ProductRequest): NetWorkResult<CustomResponse<ResponseWithMessage>> =
        handleApi { productApi.updateProduct(id, productRequest) }

    suspend fun addProduct(productRequest: ProductRequest) : NetWorkResult<CustomResponse<ResponseWithMessage>> =
        handleApi { productApi.addProduct(productRequest) }
        
    suspend fun getProductsItems(id: Int): NetWorkResult<CustomResponse<ProductItemJson>> =
        handleApi { productApi.getProductsItems(id) }

    suspend fun addProductItem(id: Int, createProductItemRequest: ProductItemRequest) : NetWorkResult<CustomResponse<ProductItemJson>> =
        handleApi { productApi.addProductItem(id, createProductItemRequest) }
}