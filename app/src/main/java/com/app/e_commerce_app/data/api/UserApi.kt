package com.app.e_commerce_app.data.api

import com.app.e_commerce_app.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequest):  Response<CustomResponse<LoginData>>

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body registerRequest: RegisterRequest) : CustomResponse<String>
}