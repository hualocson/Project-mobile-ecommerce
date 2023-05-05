package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CategoryData
import com.app.e_commerce_app.model.CustomResponse

class CategoryRemoteService:BaseRemoteService() {
    suspend fun getAllCategories(): NetWorkResult<CustomResponse<CategoryData>> =
        ApiConfig.handleApi { ApiConfig.categoryApi.getAllCategories() }
}