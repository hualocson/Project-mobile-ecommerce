package com.app.e_commerce_app.data.repository

import android.util.Log
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.CategoryRemoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryRemoteService: CategoryRemoteService) {
    suspend fun getAllCategories() = withContext(Dispatchers.IO) {
        when (val result = categoryRemoteService.getAllCategories()) {
            is NetWorkResult.Success -> {
                result.data.data
            }
            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }

    suspend fun getCategoryById(id: Int) = withContext(Dispatchers.IO) {
        when (val result = categoryRemoteService.getCategoryById(id)) {
            is NetWorkResult.Success -> {
                result.data.data
            }
            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }
}