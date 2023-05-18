package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.*
import retrofit2.Response
import retrofit2.http.*

interface SaleApi {

    @GET(ConstantsURL.NEW_URL)
    suspend fun getAllNews() : Response<CustomResponse<List<SaleJson>>>

    @POST(ConstantsURL.CREATE_NEWS)
    suspend fun createNews(@Body salesRequest: SalesRequest) : Response<CustomResponse<SaleJson>>

    @PATCH(ConstantsURL.UPDATE_NEWS)
    suspend fun updateNews(@Path("id") id: Int, @Body salesRequest: SalesRequest) : Response<CustomResponse<SaleJson>>

    @DELETE(ConstantsURL.DELETE_NEWS)
    suspend fun deleteNews(@Path("id") id: Int) : Response<CustomResponse<ResponseWithMessage>>
}