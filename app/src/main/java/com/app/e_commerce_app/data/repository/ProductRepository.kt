package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.ProductRemoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productRemoteService: ProductRemoteService) {

    suspend fun getProductsByCategory(id: Int) = withContext(Dispatchers.IO) {
        when (val response = productRemoteService.getProductsByCategory(id)) {
            is NetWorkResult.Success -> {
                response.data.data!!.products
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


    suspend fun getAllProducts() = withContext(Dispatchers.IO) {
        when (val result = productRemoteService.getAllProducts()) {
            is NetWorkResult.Success -> {
                result.data.data!!.products
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }
}