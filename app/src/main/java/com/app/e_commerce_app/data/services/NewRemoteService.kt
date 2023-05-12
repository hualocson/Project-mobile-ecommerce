package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.data.api.NewApi
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.NewJson
import javax.inject.Inject

class NewRemoteService @Inject constructor(private val newApi: NewApi) :
    BaseRemoteService() {

    suspend fun getAllNews(): NetWorkResult<CustomResponse<List<NewJson>>> =
        handleApi { newApi.getAllNews() }

}