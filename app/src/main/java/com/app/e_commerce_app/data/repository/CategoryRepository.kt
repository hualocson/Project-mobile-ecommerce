package com.app.e_commerce_app.data.repository

import android.accounts.NetworkErrorException
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.services.CategoryRemoteService
import com.app.e_commerce_app.model.CategoryRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryRemoteService: CategoryRemoteService) {
    suspend fun addCategory(categoryRequest: CategoryRequest) = withContext(Dispatchers.IO) {
        when (val result = categoryRemoteService.addCategory(categoryRequest)) {
            is NetWorkResult.Success -> {
                result.data.data ?: throw NetworkErrorException("Category Empty")
            }
            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }
    suspend fun updateCategory(id: Int, categoryRequest: CategoryRequest) = withContext(Dispatchers.IO) {
        when (val result = categoryRemoteService.updateCategory(id, categoryRequest)) {
            is NetWorkResult.Success -> {
                result.data.data ?: throw NetworkErrorException("Category Empty")
            }
            is NetWorkResult.Error -> {
                throw result.exception
            }
        }
    }



    suspend fun getAllCategories() = withContext(Dispatchers.IO) {
        when (val result = categoryRemoteService.getAllCategories()) {
            is NetWorkResult.Success -> {
                val data = result.data.data
                if (data == null || data.categories.isEmpty())
                    throw NetworkErrorException("Category Empty")
                else
                    data.categories
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