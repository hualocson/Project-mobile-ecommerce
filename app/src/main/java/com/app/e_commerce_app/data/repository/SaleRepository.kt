package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.SaleRemoteService
import com.app.e_commerce_app.model.PreSignupRequest
import com.app.e_commerce_app.model.SalesRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaleRepository @Inject constructor(private val saleRemoteService: SaleRemoteService) {
    suspend fun getAllNews()  = withContext(Dispatchers.IO) {
        when(val response = saleRemoteService.getAllNews()) {
            is NetWorkResult.Success -> {
                response.data.data
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }
    suspend fun createNews(salesRequest: SalesRequest) = withContext(Dispatchers.IO) {
        when (val result = saleRemoteService.createNews(salesRequest)) {
            is NetWorkResult.Success -> {
                result.data
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun updateNews(id: Int, salesRequest: SalesRequest) = withContext(Dispatchers.IO) {
        when (val result = saleRemoteService.updateNews(id, salesRequest)) {
            is NetWorkResult.Success -> {
                result.data
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun deleteNews(id: Int) = withContext(Dispatchers.IO) {
        when (val result = saleRemoteService.deleteNews(id)) {
            is NetWorkResult.Success -> {
                result.data
            }

            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }
}