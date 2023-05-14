package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.SaleRemoteService
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
}