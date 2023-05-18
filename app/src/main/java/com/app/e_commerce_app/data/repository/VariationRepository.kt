package com.app.e_commerce_app.data.repository

import android.accounts.NetworkErrorException
import com.app.e_commerce_app.base.network.BaseNetworkException
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.VariationRemoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VariationRepository @Inject constructor(private val variationRemoteService: VariationRemoteService) {
    suspend fun getVariationsInCategory(id: Int) = withContext(Dispatchers.IO) {
        when (val response = variationRemoteService.getVariationsInCategory(id)) {
            is NetWorkResult.Success -> {
                val data = response.data.data
                if(data.isNullOrEmpty())
                    throw NetworkErrorException("Category Empty!!")
                else
                    data
            }
            is NetWorkResult.Error -> {
                throw response.exception
            }
        }
    }

    suspend fun getAllVaritions() = withContext(Dispatchers.IO) {
        when (val result = variationRemoteService.getAllVaritions()) {
            is NetWorkResult.Success -> {
                result.data.data
            }
            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }
}