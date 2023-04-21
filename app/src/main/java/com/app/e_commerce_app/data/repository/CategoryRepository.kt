package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CategoryData
import com.app.e_commerce_app.model.CategoryModel
import com.app.e_commerce_app.model.CustomResponse

class CategoryRepository {

    suspend fun getAllCategories(): NetWorkResult<CustomResponse<CategoryData>> =
        ApiConfig.handleApi { ApiConfig.categoryApi.getAllCategories() }
}