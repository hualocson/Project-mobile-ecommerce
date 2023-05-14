package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.CategoryApi
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CategoryData
import com.app.e_commerce_app.model.CustomResponse
import javax.inject.Inject

class CategoryRemoteService @Inject constructor(private val categoryApi: CategoryApi) :
    BaseRemoteService() {
    suspend fun getAllCategories(): NetWorkResult<CustomResponse<CategoryData>> =
        handleApi { categoryApi.getAllCategories() }
}