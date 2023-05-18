package com.app.e_commerce_app.data.repository

import android.util.Log
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.ProductRemoteService
import com.app.e_commerce_app.model.product.ProductItemRequest
import com.app.e_commerce_app.model.product.ProductRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productRemoteService: ProductRemoteService) {

    suspend fun getProductsByCategory(id: Int) = withContext(Dispatchers.IO) {
        when (val response = productRemoteService.getProductsByCategory(id)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun getProductsById(id: Int) = withContext(Dispatchers.IO) {
        when (val response = productRemoteService.getProductsById(id)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun getProductsItems(id: Int) = withContext(Dispatchers.IO) {
        when (val response = productRemoteService.getProductsItems(id)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }


    suspend fun getAllProducts() = withContext(Dispatchers.IO) {
        when (val result = productRemoteService.getAllProducts()) {
            is NetWorkResult.Success -> {
                result.data.data!!
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun updateProduct(id: Int, productRequest: ProductRequest) = withContext(Dispatchers.IO) {
        when (val response = productRemoteService.updateProduct(id, productRequest)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun addProduct(productRequest: ProductRequest) = withContext(Dispatchers.IO) {
        when (val response = productRemoteService.addProduct(productRequest)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }
    suspend fun addProductItem(id: Int, createProductItemRequest: ProductItemRequest) = withContext(Dispatchers.IO) {
        when (val response = productRemoteService.addProductItem(id, createProductItemRequest)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun updateProductItem(itemId: Int, createProductItemRequest: ProductItemRequest) = withContext(Dispatchers.IO) {
        when (val response = productRemoteService.updateProductItem(itemId, createProductItemRequest)) {
            is NetWorkResult.Success -> {
                response.data.data!!
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

}