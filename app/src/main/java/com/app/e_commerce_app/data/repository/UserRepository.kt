package com.app.e_commerce_app.data.repository

import com.app.e_commerce_app.data.api.ApiConfig
import com.app.e_commerce_app.data.api.NetWorkResult
import com.app.e_commerce_app.model.CustomResponse
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.model.RegisterRequest
import com.app.e_commerce_app.model.UserJson
import com.app.e_commerce_app.model.token.TokenJson

class UserRepository {
    suspend fun login(loginRequest: LoginRequest): NetWorkResult<CustomResponse<TokenJson>> =
        ApiConfig.handleApi { ApiConfig.userApi.login(loginRequest) }

    suspend fun getUserProfile(): NetWorkResult<CustomResponse<UserJson>> =
        ApiConfig.handleApi { ApiConfig.userApi.getUserProfile() }

    suspend fun register(registerRequest: RegisterRequest) =
        ApiConfig.userApi.register(registerRequest)
}