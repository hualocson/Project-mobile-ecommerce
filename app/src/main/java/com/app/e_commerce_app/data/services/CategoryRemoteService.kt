package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.CategoryApi
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.CategoryData
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CategoryRequest
import javax.inject.Inject

class CategoryRemoteService @Inject constructor(private val categoryApi: CategoryApi) :
    BaseRemoteService() {

    suspend fun addCategory(categoryRequest: CategoryRequest): NetWorkResult<CustomResponse<CategoryModel>> =
        handleApi { categoryApi.addCategory(categoryRequest) }

    suspend fun getAllCategories(): NetWorkResult<CustomResponse<CategoryData>> =
        handleApi { categoryApi.getAllCategories() }

    suspend fun getCategoryById(id: Int): NetWorkResult<CustomResponse<CategoryModel>> =
        handleApi { categoryApi.getCategoryById(id) }

    suspend fun updateCategory(id: Int, categoryRequest: CategoryRequest): NetWorkResult<CustomResponse<CategoryModel>> =
        handleApi { categoryApi.updateCategory(id, categoryRequest) }
}