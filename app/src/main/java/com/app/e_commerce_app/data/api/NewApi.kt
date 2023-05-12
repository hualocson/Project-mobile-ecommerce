package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.*
import retrofit2.Response
import retrofit2.http.GET

interface NewApi {

    @GET(ConstantsURL.NEW_URL)
    suspend fun getAllNews() : Response<CustomResponse<List<NewJson>>>
}