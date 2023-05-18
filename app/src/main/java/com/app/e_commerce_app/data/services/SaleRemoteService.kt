package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.SaleApi
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.ResponseWithMessage
import com.app.e_commerce_app.model.SaleJson
import com.app.e_commerce_app.model.SalesRequest
import javax.inject.Inject

class SaleRemoteService @Inject constructor(private val saleApi: SaleApi) :
    BaseRemoteService() {

    suspend fun getAllNews(): NetWorkResult<CustomResponse<List<SaleJson>>> =
        handleApi { saleApi.getAllNews() }

    suspend fun createNews(salesRequest: SalesRequest): NetWorkResult<CustomResponse<SaleJson>> =
        handleApi { saleApi.createNews(salesRequest) }

    suspend fun updateNews(id: Int, salesRequest: SalesRequest): NetWorkResult<CustomResponse<SaleJson>> =
        handleApi { saleApi.updateNews(id, salesRequest) }

    suspend fun deleteNews(id: Int): NetWorkResult<CustomResponse<ResponseWithMessage>> =
        handleApi { saleApi.deleteNews(id) }
}