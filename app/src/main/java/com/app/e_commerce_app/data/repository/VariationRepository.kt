package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.VariationRemoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VariationRepository @Inject constructor(private val variationRemoteService: VariationRemoteService) {
    suspend fun getVariationsInCategory(id: Int) = withContext(Dispatchers.IO) {
        when (val response = variationRemoteService.getVariationsInCategory(id)) {
            is NetWorkResult.Success -> {
                response.data.data
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }
}