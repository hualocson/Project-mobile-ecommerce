package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.SaleApi
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.SaleJson
import javax.inject.Inject

class SaleRemoteService @Inject constructor(private val saleApi: SaleApi) :
    BaseRemoteService() {

    suspend fun getAllNews(): NetWorkResult<CustomResponse<List<SaleJson>>> =
        handleApi { saleApi.getAllNews() }

}