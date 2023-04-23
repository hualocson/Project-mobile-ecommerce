package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CategoryData
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.ProductData

class ProductRespository {
    suspend fun getAllProducts(): NetWorkResult<CustomResponse<ProductData>> =
        ApiConfig.handleApi { ApiConfig.productApi.getAllProducts() }
}