package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.LoginData
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.model.RegisterRequest

class UserRepository {
    suspend fun login(loginRequest: LoginRequest): NetWorkResult<CustomResponse<LoginData>> =
        ApiConfig.handleApi { ApiConfig.userApi.login(loginRequest) }

    suspend fun register(registerRequest: RegisterRequest) =
        ApiConfig.userApi.register(registerRequest)
}